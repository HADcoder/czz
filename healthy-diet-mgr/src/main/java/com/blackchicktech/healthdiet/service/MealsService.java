package com.blackchicktech.healthdiet.service;


import com.blackchicktech.healthdiet.domain.MealsRecommendationResponse;
import com.blackchicktech.healthdiet.domain.RecommendRecipeInfo;
import com.blackchicktech.healthdiet.entity.*;
import com.blackchicktech.healthdiet.repository.*;
import com.blackchicktech.healthdiet.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MealsService {

    private static Logger LOGGER = LoggerFactory.getLogger(MealsService.class);

    @Autowired
    private MealsDaoImpl mealsDao;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private RecipeDaoImpl recipeDao;

    @Autowired
    private FoodDaoImpl foodDao;

    @Autowired
    private RecipeWeightDaoImpl recipeWeightDao;

    public MealsRecommendationResponse getRecommendedMeals(String openId) {
        User user = userDao.getUserByOpenId(openId);
        float standardWeight = calStandardWeight(user);
        int nephroticPeriod = Integer.valueOf(user.getNephroticPeriod());
        List<String> otherDiseases = StringUtils.isEmpty(user.getOtherDiseases()) ? null : Arrays.asList(user.getOtherDiseases().split(","));

        MealsRecommendationResponse recommendedMeals = new MealsRecommendationResponse();
        int standardWeightRange = deduceStandardWeightRange(standardWeight);

        String ckd;
        if (nephroticPeriod <= 2) {
            ckd = "CKD1-2";
        } else if (nephroticPeriod == 3) {
            ckd = "CKD3";
        } else {
            ckd = "CKD4-5";
        }
        FoodRecommended foodRecommended = mealsDao.getFoodRecommendedByStdWgtAndCkd(standardWeightRange, ckd);
        List<String> recipeWeights = deduceRecipeWeight(otherDiseases);
        List<String> recipeCookingMethod = deduceRecipeCookingMethod(otherDiseases);
        recommendedMeals.setBreakfast(deduceRecommendedBreakfast(foodRecommended, recipeWeights, recipeCookingMethod));
        recommendedMeals.setLunch(deduceRecommendedLunch(foodRecommended, recipeWeights, recipeCookingMethod));
        recommendedMeals.setDinner(deduceRecommendedDinner(foodRecommended, recipeWeights, recipeCookingMethod));
        recommendedMeals.setAdditionMeal(deduceRecommendedAdditionalMeal(foodRecommended, recipeWeights, recipeCookingMethod));
        return recommendedMeals;
    }

    private List<RecommendRecipeInfo> deduceRecommendedBreakfast(FoodRecommended foodRecommended, List<String> recipeWeights, List<String> recipeCookingMethod) {
        List<RecommendRecipeInfo> breakfast = new ArrayList<>();
        List<String> breakfastElements = candidateFoodElements(foodRecommended, "BR");
        for (String element : breakfastElements) {
            LOGGER.info("Trying to get breakfast food for element: {}", element);
            Set<String> ckds = Constants.CKD_FOOD_CATAGARIES.get(element);
            for (String ckd : ckds) {
                com.blackchicktech.healthdiet.domain.Recipe recipe = recipeDao.getRecipeByCkdCategory(ckd, recipeWeights, recipeCookingMethod, true);
                if (recipe != null) {
                    String material = recipe.getMaterial();
                    String recipeId = recipe.getRecipeId();
                    String recipeName = recipe.getRecipeName();

                    material = material.indexOf("|") > -1 ? material.substring(1, material.length() - 1) : material;
                    FoodUnit food = foodDao.getFoodUnitByAlias(material);
                    if (food != null) {
                        float foodProtein = food.getProtein();
                        int foodEdible = food.getEdible();
                        double protein = deduceCandidateFoodFieldValue(foodRecommended, element, "BP");
                        RecommendRecipeInfo recommendRecipeInfo = new RecommendRecipeInfo();
                        recommendRecipeInfo.setRecipeName(recipeName);
                        recommendRecipeInfo.setRecipeId(recipeId);
                        Map<String, Integer> materialMap = new HashMap<>();
                        materialMap.put(material, BigDecimal.valueOf(protein / foodProtein / foodEdible * 10000)
                                .setScale(0, BigDecimal.ROUND_FLOOR)
                                .intValue());
                        recommendRecipeInfo.setMaterials(materialMap);
                        recommendRecipeInfo.setProtein(protein);
                        breakfast.add(recommendRecipeInfo);
                    }

                }

            }
        }
        return breakfast;

    }

    private List<String> deduceRecipeWeight(List<String> otherDiseases) {
        List<String> recipeWeight = new ArrayList<>();
        recipeWeight.add("protein_weight");
        if (otherDiseases != null) {
            for (String otherDisease : otherDiseases) {
                recipeWeight.add(Constants.WEIGHT_FILED_DISEASE_MAP.get(otherDisease));
            }
        }
        return recipeWeight;
    }

    private List<String> deduceRecipeCookingMethod(List<String> otherDiseases) {
        if (otherDiseases != null) {
            List<String> cookingMethod = new ArrayList<>();
            for (String otherDisease : otherDiseases) {
                cookingMethod.addAll(Constants.COOKING_FILETER.get(otherDisease));
            }
            return cookingMethod;
        }
        return null;

    }

    private List<RecommendRecipeInfo> deduceRecommendedLunch(FoodRecommended foodRecommended, List<String> recipeWeights, List<String> recipeCookingMethod) {
        return deduceRecommendedMeal(foodRecommended, "LR", "LP", recipeWeights, recipeCookingMethod);

    }

    private List<RecommendRecipeInfo> deduceRecommendedDinner(FoodRecommended foodRecommended, List<String> recipeWeights, List<String> recipeCookingMethod) {
        return deduceRecommendedMeal(foodRecommended, "DR", "DP", recipeWeights, recipeCookingMethod);

    }


    private List<RecommendRecipeInfo> deduceRecommendedAdditionalMeal(FoodRecommended foodRecommended, List<String> recipeWeights, List<String> recipeCookingMethod) {
        List<RecommendRecipeInfo> recommendAdditionalMeal = new ArrayList<>();
        List<String> breakfastElements = candidateFoodElements(foodRecommended, "AR");
        for (String element : breakfastElements) {
            LOGGER.info("Trying to get additional meal food for element: {}", element);
            Set<String> ckds = Constants.CKD_FOOD_CATAGARIES.get(element);
            for (String ckd : ckds) {
                com.blackchicktech.healthdiet.domain.Recipe recipe = recipeDao.getRecipeByCkdCategory(ckd, recipeWeights, recipeCookingMethod, false);
                if (recipe != null) {
                    if (recipe != null) {
                        String material = recipe.getMaterial();
                        String recipeId = recipe.getRecipeId();
                        String recipeName = recipe.getRecipeName();
                        material = material.indexOf("|") > -1 ? material.substring(1, material.length() - 1) : material;
                        FoodUnit food = foodDao.getFoodUnitByAlias(material);
                        if (food != null) {
                            float foodProtein = food.getProtein();
                            int foodEdible = food.getEdible();
                            double protein = deduceCandidateFoodFieldValue(foodRecommended, element, "AP");
                            RecommendRecipeInfo recommendRecipeInfo = new RecommendRecipeInfo();
                            recommendRecipeInfo.setRecipeName(recipeName);
                            recommendRecipeInfo.setRecipeId(recipeId);
                            Map<String, Integer> materialMap = new HashMap<>();
                            materialMap.put(material, BigDecimal.valueOf(protein / foodProtein / foodEdible * 10000)
                                    .setScale(0, BigDecimal.ROUND_FLOOR)
                                    .intValue());
                            recommendRecipeInfo.setMaterials(materialMap);
                            recommendRecipeInfo.setProtein(protein);
                            recommendAdditionalMeal.add(recommendRecipeInfo);
                        }
                    }

                }
            }

        }
        return recommendAdditionalMeal;
    }

    private List<RecommendRecipeInfo> deduceRecommendedMeal(FoodRecommended foodRecommended, String suffixR, String suffixP, List<String> recipeWeights, List<String> recipeCookingMethod) {
        List<RecommendRecipeInfo> recommendRecipes = new ArrayList<>();
        List<String> breakfastElements = candidateFoodElements(foodRecommended, suffixR);
        for (String element : breakfastElements) {

            Set<String> ckds = Constants.CKD_FOOD_CATAGARIES.get(element);
            boolean hasETypeYet = false;
            for (String ckd : ckds) {
                if (hasETypeYet && (ckd.contains("E"))) {
                    continue;
                }
                com.blackchicktech.healthdiet.domain.Recipe recipe;
                if ((ckd.contains("E"))) {
                    hasETypeYet = true;
                    recipe = recipeDao.getMeatRecipeByCkdCategory(ckd, recipeWeights, recipeCookingMethod);
                } else {
                    recipe = recipeDao.getRecipeByCkdCategory(ckd, recipeWeights, recipeCookingMethod, false);
                }

                if (recipe != null) {
                    String material = recipe.getMaterial();
                    String recipeId = recipe.getRecipeId();
                    String recipeName = recipe.getRecipeName();

                    RecommendRecipeInfo recommendRecipeInfo = new RecommendRecipeInfo();
                    recommendRecipeInfo.setRecipeName(recipeName);
                    recommendRecipeInfo.setRecipeId(recipeId);
                    double protein = deduceCandidateFoodFieldValue(foodRecommended, element, suffixP);
                    recommendRecipeInfo.setProtein(protein);
                    Map<String, Integer> materialMap = new HashMap<>();
                    material = material.substring(1, material.length() - 1);
                    if (material.indexOf("|") > -1) {
                        String[] foodElements = material.split("\\|");
                        for (String foodElement : foodElements) {
                            foodElement = foodElement.indexOf("|") > -1 ? foodElement.substring(1, foodElement.length() - 1) : foodElement;
                            FoodUnit food = foodDao.getFoodUnitByAlias(foodElement);
                            if (food != null) {
                                float foodProtein = food.getProtein();
                                int foodEdible = food.getEdible();
                                String alias = food.getFoodAlias().substring(1, food.getFoodAlias().length() - 1);
                                alias = alias.indexOf("|") > -1 ? alias.split("\\|")[0] : alias;
                                materialMap.put(alias, BigDecimal.valueOf(protein / foodProtein
                                        / foodEdible * 10000)
                                        .setScale(0, BigDecimal.ROUND_FLOOR)
                                        .intValue());

                            }
                        }
                    } else {
                        material = material.indexOf("|") > -1 ? material.substring(1, material.length() - 1) : material;
                        FoodUnit food = foodDao.getFoodUnitByAlias(material);
                        if (food != null) {
                            float foodProtein = food.getProtein();
                            int foodEdible = food.getEdible();
                            String alias = food.getFoodAlias().substring(1, food.getFoodAlias().length() - 1);
                            alias = alias.indexOf("|") > -1 ? alias.split("\\|")[0] : alias;
                            materialMap.put(alias, BigDecimal.valueOf(protein / foodProtein
                                    / foodEdible * 10000)
                                    .setScale(0, BigDecimal.ROUND_FLOOR)
                                    .intValue());
                        }

                    }

                    recommendRecipeInfo.setMaterials(materialMap);
                    recommendRecipes.add(recommendRecipeInfo);
                }

            }
        }
        return recommendRecipes;
    }


    public float calStandardWeight(User user) {
        String gender = user.getGender();
        String height = getHeightOrWeight(user.getHeight());
        if ("male".equals(gender) || Constants.GENDER.getOrDefault("male", "").equals(gender)) {
            return (float) ((Integer.parseInt(height) - 100) * 0.9);
        } else if ("female".equals(gender) || Constants.GENDER.getOrDefault("female", "").equals(gender)) {
            return (float) ((Integer.parseInt(height) - 100) * 0.9 - 2.5);
        } else {
            throw new IllegalArgumentException("Gender can only be male or female");
        }
    }

    private String getHeightOrWeight(String value) {
        Matcher result = Pattern.compile("(\\d+(.\\d))?").matcher(value);
        return result.find() ? result.group() : "0";
    }

    public int deduceStandardWeightRange(float standardWeight) {
        if (standardWeight < 40) {
            return 40;
        } else if (standardWeight > 75) {
            return 75;
        } else {
            return (int) standardWeight;
        }
    }

    public List<String> candidateFoodElements(FoodRecommended foodRecommended, String suffix) {
        List<String> candidateFoodElements = new ArrayList<>();
        Method[] methods = FoodRecommended.class.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && methodName.endsWith(suffix)) {
                try {
                    double quantity = (Double) method.invoke(foodRecommended);
                    if (quantity > 0) {
                        String candidateFoodElement = methodName.substring(3, methodName.length() - 2);
                        candidateFoodElements.add(candidateFoodElement);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return candidateFoodElements;
    }

    public double deduceCandidateFoodFieldValue(FoodRecommended foodRecommended, String element, String suffix) {

        Method[] methods = FoodRecommended.class.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && methodName.endsWith(suffix) && StringUtils.containsIgnoreCase(methodName, element)) {
                try {
                    return (Double) method.invoke(foodRecommended);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

}
