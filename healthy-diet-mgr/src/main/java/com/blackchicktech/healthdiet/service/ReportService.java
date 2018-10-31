package com.blackchicktech.healthdiet.service;

import com.blackchicktech.healthdiet.domain.*;
import com.blackchicktech.healthdiet.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReportService {

    private static Logger LOGGER = LoggerFactory.getLogger(ReportService.class);

    public static Random random = new Random();


    public ReportResponse report(ReportRequest reportRequest) {
        LOGGER.info("Begin to generate report for user openId={}", reportRequest.getUserInfo().getOpenId());
        ReportResponse response = new ReportResponse();
        response.setStandardWeight(String.valueOf(calStandardWeight(reportRequest)) + "公斤");
        response.setCalorie(String.format("%.2f 卡路里", calCalorie(reportRequest)));
        response.setProtein(calProtein(reportRequest));
        response.setBmi(calBmi(reportRequest));
        response.setAdvice(deduceAdvice(reportRequest));
        response.setSuggestNutrition(calSuggestNutrition(reportRequest));
        response.setSlogan(deduceSlogan());
        LOGGER.info("End to generate report for user openId={}", reportRequest.getUserInfo().getOpenId());
        LOGGER.debug("Report info={}", response.toString());
        return response;
    }

    private String calBmi(ReportRequest reportRequest) {
        float bmiIndex = calBmiIndex(reportRequest);
        if (bmiIndex < 18.5) {
            return BodyType.MALNOURISHED.info();
        } else if (bmiIndex >= 18.5 && bmiIndex <= 23.9) {
            return BodyType.NORMAL.info();
        } else if (bmiIndex >= 24.0 && bmiIndex <= 27.9) {
            return BodyType.OVERWEIGHT.info();
        } else {
            return BodyType.FATTY.info();
        }
    }

    private float calBmiIndex(ReportRequest reportRequest) {
        String height = reportRequest.getUserDataInfo().getHeight();
        String weight = reportRequest.getUserDataInfo().getWeight();
        return (Float.parseFloat(getHeightOrWeight(weight)) / (Float.parseFloat(getHeightOrWeight(height)) / 100))
                / (Float.parseFloat(getHeightOrWeight(height)) / 100);
    }

    public float calStandardWeight(ReportRequest reportRequest) {
        String gender = reportRequest.getUserDataInfo().getGender();
        String height = getHeightOrWeight(reportRequest.getUserDataInfo().getHeight());
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

    private float calCalorie(ReportRequest reportRequest) {
        String sportRate = reportRequest.getUserDataInfo().getSportRate();
        float standardWeight = calStandardWeight(reportRequest);
        float bmi = calBmiIndex(reportRequest);
        if ("light".equals(sportRate)
                || Constants.SPORT_RATE.getOrDefault("light", "").equals(sportRate)) {
            if (bmi < 18.5) {
                return standardWeight * 35;
            } else if (bmi >= 18.5 && bmi < 23.9) {
                return (float) (standardWeight * 32.5);
            } else {
                return standardWeight * 30;
            }
        } else {
            if (bmi < 18.5) {
                return standardWeight * 40;
            } else if (bmi >= 18.5 && bmi < 23.9) {
                return (standardWeight * 37);
            } else {
                return standardWeight * 35;
            }
        }


    }

    private String calProtein(ReportRequest reportRequest) {
        int nephroticPeriod = Integer.parseInt(reportRequest.getUserDataInfo().getNephroticPeriod());
        List<String> treatmentMethod = reportRequest.getUserDataInfo().getTreatmentMethod();
        BigDecimal standardWeight = BigDecimal.valueOf(calStandardWeight(reportRequest));
        StringBuilder protein = new StringBuilder();
        if (nephroticPeriod >= 1 && nephroticPeriod <= 2) {
            return protein.append(standardWeight.multiply(BigDecimal.valueOf(1)).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                    .append(standardWeight.multiply(BigDecimal.ONE).setScale(1, BigDecimal.ROUND_FLOOR)).append("克").toString();
        } else if (nephroticPeriod == 3) {
            if (treatmentMethod.stream().anyMatch(item -> item.contains("dialysis"))) {
                return protein.append(standardWeight.multiply(BigDecimal.ONE).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                        .append(standardWeight.multiply(BigDecimal.valueOf(1.2)).setScale(1, BigDecimal.ROUND_FLOOR)).append("克").toString();
            } else {
                return protein.append(standardWeight.multiply(BigDecimal.valueOf(0.8)).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                        .append(standardWeight.multiply(BigDecimal.valueOf(10.8)).setScale(1, BigDecimal.ROUND_FLOOR)).append("克").toString();
            }
        } else if (nephroticPeriod > 3 && nephroticPeriod <= 5) {
            if (treatmentMethod.stream().anyMatch(item -> item.contains("dialysis"))) {
                return protein.append(standardWeight.multiply(BigDecimal.ONE).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                        .append(standardWeight.multiply(BigDecimal.valueOf(1.2)).setScale(1, BigDecimal.ROUND_FLOOR)).append("克").toString();
            } else {
                return protein.append(standardWeight.multiply(BigDecimal.valueOf(0.6)).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                        .append(standardWeight.multiply(BigDecimal.valueOf(10.8)).setScale(1, BigDecimal.ROUND_FLOOR)).append("克").toString();
            }
        }
        return "Protein is unclear.";
    }

    public List<SuggestNutrition> calSuggestNutrition(ReportRequest reportRequest) {
        float standardWeight = calStandardWeight(reportRequest);

        List<String> irritabilities = reportRequest.getUserDataInfo().getIrritability();
        int nephroticPeriod = Integer.valueOf(reportRequest.getUserDataInfo().getNephroticPeriod());
        List<SuggestNutrition> suggestNutritions;
        boolean irritableToMilk = irritabilities != null && irritabilities.contains("milk")
                || irritabilities != null && irritabilities.contains(Constants.IRRITABILITY.getOrDefault("milk", ""));
        if (nephroticPeriod <= 2) {
            LOGGER.info("NephroticPeriod is under 3, it should use 1 CKD food specification.");
            suggestNutritions = deduceSuggestNutritionsForLowPeriod(standardWeight, irritableToMilk);

        } else if (nephroticPeriod == 3) {
            LOGGER.info("NephroticPeriod is above 3, it should use 0.8 CKD food specification.");
            suggestNutritions = deduceSuggestNutritionsForHighPeriod(standardWeight, irritableToMilk);
        }  else if (nephroticPeriod > 3 && nephroticPeriod <= 5) {
            LOGGER.info("NephroticPeriod is above 3, it should use 0.6 CKD food specification.");
            suggestNutritions = deduceSuggestNutritionsForHighPeriod(standardWeight, irritableToMilk);
        } else {
            throw new RuntimeException("NephroticPeriod can only be in the range from 1 to 5");
        }
        return suggestNutritions;
    }

    private List<SuggestNutrition> deduceSuggestNutritionsForLowPeriod(float standardWeight, boolean irritableToMilk) {
        List<SuggestNutrition> suggestNutritions = new ArrayList<>();

        if (standardWeight >= 40 && standardWeight < 45 || standardWeight <= 40) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_40_8[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_40_8[i]) + "克"));
                }
            }

        } else if (standardWeight >= 45 && standardWeight < 50) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_45_8[i])+ "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_45_8[i]) + "克"));
                }
            }
        } else if (standardWeight >= 50 && standardWeight < 55) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_50_8[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_50_8[i] )+ "克"));
                }
            }
        } else if (standardWeight >= 55 && standardWeight < 60) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_55_8[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_55_8[i] )+ "克"));
                }
            }
        } else if (standardWeight >= 60 && standardWeight < 65) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_60_8[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_60_8[i]) + "克"));
                }
            }
        } else if (standardWeight >= 65 && standardWeight < 70) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_65_8[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_65_8[i] )+ "克"));
                }
            }
        } else if (standardWeight >= 70 && standardWeight < 75) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_70_8[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_70_8[i] )+ "克"));
                }
            }
        } else if (standardWeight >= 75) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_75_8[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_75_8[i]) + "克"));
                }
            }

        } else {
            suggestNutritions.add(new SuggestNutrition("没有合适的食材", "0克"));
        }
        return suggestNutritions;
    }

    private List<SuggestNutrition> deduceSuggestNutritionsForHighPeriod(float standardWeight, boolean irritableToMilk) {
        List<SuggestNutrition> suggestNutritions = new ArrayList<>();


        if (standardWeight >= 40 && standardWeight < 45 || standardWeight < 40) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_40_6[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_40_6[i]) + "克"));
                }
            }

        } else if (standardWeight >= 45 && standardWeight < 50) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_45_6[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_45_6[i]) + "克"));
                }
            }
        } else if (standardWeight >= 50 && standardWeight < 55) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_50_6[i] )+ "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_50_6[i]) + "克"));
                }
            }
        } else if (standardWeight >= 55 && standardWeight < 60) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_55_6[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_55_6[i]) + "克"));
                }
            }
        } else if (standardWeight >= 60 && standardWeight < 65) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_60_6[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_60_6[i]) + "克"));
                }
            }
        } else if (standardWeight >= 65 && standardWeight < 70) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_65_6[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_65_6[i]) + "克"));
                }
            }
        } else if (standardWeight >= 70 && standardWeight < 75) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_70_6[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_70_6[i]) + "克"));
                }
            }
        } else if (standardWeight >= 75) {
            if (irritableToMilk) {

                for (int i = 0; i < Constants.CKD_FOOD_WITHOUT_MILK.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD_WITHOUT_MILK[i],
                            (int)(Constants.CKD_FOOD_WITHOUT_MILK_SPEC_BASE
                                    .get(Constants.CKD_FOOD_WITHOUT_MILK[i])
                                    * Constants.CKD_FOOD_WITHOUTMILK_75_6[i]) + "克"));
                }
            } else {
                for (int i = 0; i < Constants.CKD_FOOD.length; i++) {

                    suggestNutritions.add(new SuggestNutrition(Constants.CKD_FOOD[i],
                            (int)(Constants.CKD_FOOD_SPEC_BASE
                                    .get(Constants.CKD_FOOD[i])
                                    * Constants.CKD_FOOD_75_6[i]) + "克"));
                }
            }

        } else {
            suggestNutritions.add(new SuggestNutrition("没有合适的食材", "0克"));
        }

        return suggestNutritions;
    }

    private String deduceAdvice(ReportRequest reportRequest) {
        int nephroticPeriod = Integer.parseInt(reportRequest.getUserDataInfo().getNephroticPeriod());
        List<String> otherDiseases = reportRequest.getUserDataInfo().getOtherDisease();
        StringBuilder suggestDiets = new StringBuilder();
        StringBuilder otherDiseaseResult = new StringBuilder();
        for (String otherDisease : otherDiseases) {
            OtherDiseaseSuggestDiet otherDiseaseSuggestDiet = Constants.SUGGESTED_DIET.get(otherDisease);
            if (otherDiseaseSuggestDiet != null) {
                String separator = suggestDiets.length() > 0 ? "、" : "";
                suggestDiets.append(separator).append(otherDiseaseSuggestDiet.getSuggestDiet());
                otherDiseaseResult.append(separator).append(otherDiseaseSuggestDiet.getElement());
            }
        }
        if (!otherDiseases.isEmpty()) {
            LOGGER.info("User has other diseases, use ADVICE_TEMPLATE.");
            return String.format(Constants.ADVICE_TEMPLATE, nephroticPeriod,
                    otherDiseaseResult.toString(), suggestDiets.toString());
        } else {
            LOGGER.info("User has no other diseases, use WITHOUT_NEOPATHY_ADVICE_TEMPLATE.");
            return String.format(Constants.WITHOUT_NEOPATHY_ADVICE_TEMPLATE, nephroticPeriod);
        }

    }

    private String deduceSlogan() {
        int seed = random.nextInt(10);
        System.out.println("Seed: " + seed);
        return Constants.SLOGAN[seed % Constants.SLOGAN.length];
    }


}
