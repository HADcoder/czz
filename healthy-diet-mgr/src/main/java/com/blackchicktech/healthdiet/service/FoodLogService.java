package com.blackchicktech.healthdiet.service;

import com.blackchicktech.healthdiet.domain.AccumulativeEnergy;
import com.blackchicktech.healthdiet.domain.FoodLogItem;
import com.blackchicktech.healthdiet.domain.FoodLogRequest;
import com.blackchicktech.healthdiet.domain.MonthFoodLog;
import com.blackchicktech.healthdiet.domain.ThreeDayFoodLogAnalysis;
import com.blackchicktech.healthdiet.entity.FoodLog;
import com.blackchicktech.healthdiet.entity.FoodLogDetail;
import com.blackchicktech.healthdiet.entity.FoodTbl;
import com.blackchicktech.healthdiet.entity.User;
import com.blackchicktech.healthdiet.repository.FoodDaoImpl;
import com.blackchicktech.healthdiet.repository.FoodLogDao;
import com.blackchicktech.healthdiet.repository.UserDaoImpl;
import com.blackchicktech.healthdiet.util.Constants;
import com.blackchicktech.healthdiet.util.FoodLogUtil;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FoodLogService {

    private static final Logger logger = LoggerFactory.getLogger(FoodLogService.class);

    private static final long ONE_DAY_MILI_SECONDS = 1000 * 3600 * 24;

    @Autowired
    private FoodLogDao foodLogDao;

    @Autowired
    private FoodDaoImpl foodDao;

    @Autowired
    private UserDaoImpl userDao;


    public List<MonthFoodLog> getCurrentMonthFoodLog(String openId, Date currentDate) {
        return foodLogDao.getCurrentMonthFoodLog(openId, currentDate).stream()
                .map(MonthFoodLog::new).collect(Collectors.toList());
    }

    public List<MonthFoodLog> getAllDaysFoodLog(String openId) {
        return foodLogDao.getAllDaysFoodLog(openId).stream()
                .map(MonthFoodLog::new).collect(Collectors.toList());
    }

    public List<FoodLogDetail> getFoodLogDetail(String openId, Date date, String mealtime) {
        return foodLogDao.getFoodLogDetailByDate(openId, date, mealtime);
    }

    public FoodLog getMonthFoodLog(String openId, Date date) {
        return foodLogDao.getFoodLogByDate(openId, date).stream().findFirst().orElse(null);
    }

    public void updateIsCompletedLog(String openId, Date date, boolean checked) {
        foodLogDao.updateCompletedLog(openId, date, checked);
    }

    public AccumulativeEnergy updateFoodLog(FoodLogRequest request) {
        if (request.getFoodLogItemList() != null && !request.getFoodLogItemList().isEmpty()) {
            foodLogDao.addFoodLogDetail(request);
        } else {
            foodLogDao.deleteFoodLogDetail(request);
        }

        //获取全天记录的食物计算能量
        List<FoodLogDetail> todayDetail = foodLogDao.getFoodLogDetailByDate(request.getOpenId(), request.getDate(), null);
        if (todayDetail.isEmpty()) {
            //全天没有食物，删除当日食物记录
            foodLogDao.deleteFoodLog(request.getOpenId(), request.getDate());
            return AccumulativeEnergy.emptyEnergy();
        }

        AccumulativeEnergy accumulativeEnergy = new AccumulativeEnergy();
        for (FoodLogDetail foodLogDetail : todayDetail) {

            //计算每一个食材的能量
            for (FoodLogItem foodLogItem : FoodLogUtil.readFromJson(foodLogDetail.getContent())) {
                calEnergy(accumulativeEnergy, foodLogItem);
                accumulativeEnergy.setEmpty(false);
            }
        }

        if (!accumulativeEnergy.isEmpty()) {
            //持久化每日能量
            FoodLog foodLog = new FoodLog(request.getOpenId(), request.getDate(),
                    false,
                    accumulativeEnergy);
            foodLogDao.addFoodLog(foodLog);
        } else {
            foodLogDao.deleteFoodLog(request.getOpenId(), request.getDate());
        }
        return accumulativeEnergy;
    }

    private void calEnergy(AccumulativeEnergy accumulativeEnergy, FoodLogItem foodLogItem) {
        FoodTbl foodTbl = foodDao.getFoodById(foodLogItem.getFoodId());
        double calPercent = getCalPercent(foodLogItem.getChannel(), foodTbl);

        //根据每日摄入计算百分比
        calPercent = calPercent * (readDouble(foodLogItem.getUnit()) / 100);

        double totalEnergy = accumulativeEnergy.getTotalEnergy() + foodTbl.getEnergy() * calPercent;
        double totalProtein = accumulativeEnergy.getTotalProtein() + foodTbl.getProtein() * calPercent;
        double peRatio = (accumulativeEnergy.getPeRatio() * accumulativeEnergy.getTotalProtein()) + calPeRatio(foodTbl, totalProtein, calPercent);

        double totalFat = accumulativeEnergy.getFat() + readDouble(foodTbl.getFat(), 0) * calPercent;
        double feRatio = calFaRatio(totalEnergy, totalFat);

        double totalCho = accumulativeEnergy.getCho() + readDouble(foodTbl.getCho(), 0) * calPercent;
        double ceRatio = calChoRatio(totalEnergy, totalCho);

        double na = accumulativeEnergy.getNa() + readDouble(foodTbl.getNa(), 0) * calPercent;
        double k = accumulativeEnergy.getK() + readDouble(foodTbl.getK(), 0) * calPercent;
        double p = accumulativeEnergy.getP() + readDouble(foodTbl.getP(), 0) * calPercent;
        double ca = accumulativeEnergy.getCa() + readDouble(foodTbl.getCa(), 0) * calPercent;
        //应该将计算重构到最后?

        accumulativeEnergy.setTotalEnergy(totalEnergy);
        accumulativeEnergy.setTotalProtein(totalProtein);
        accumulativeEnergy.setPeRatio(peRatio / totalProtein);
        accumulativeEnergy.setFat(totalFat);
        accumulativeEnergy.setFeRatio(feRatio);
        accumulativeEnergy.setCho(totalCho);
        accumulativeEnergy.setCeRatio(ceRatio);
        accumulativeEnergy.setNa(na);
        accumulativeEnergy.setK(k);
        accumulativeEnergy.setP(p);
        accumulativeEnergy.setCa(ca);
    }

    //通过超市或者市场按照食补计算百分比
    private double getCalPercent(String channel, FoodTbl foodTbl) {
        if ("超市".equals(channel)) {
            return 1;
        } else if ("市场".equals(channel)) {
            return ((double) foodTbl.getEdible()) / 100;
        }

        logger.warn("Unknown channel ={} cal as 100 percent", channel);
        return 1;
    }

    //蛋白质供能比
    private double calPeRatio(FoodTbl foodTbl, double totalProtein, double calPercent) {
        if ("3".equals(foodTbl.getFoodCode()) && "1".equals(foodTbl.getSubCode())
                || (Integer.valueOf(foodTbl.getFoodCode()) >= 8
                && Integer.valueOf(foodTbl.getFoodCode()) <= 12)) {
            return foodTbl.getProtein() * calPercent;
        }
        return 0;
    }

    //脂肪供能比
    private double calFaRatio(double totalEnergy, double totalFat) {
        return totalFat * 9 / totalEnergy;
    }

    //碳水化物供能bi
    private double calChoRatio(double totalEnergy, double totalCho) {
        return totalCho * 4 / totalEnergy;
    }

    private double readDouble(String dStr, double defaultValue) {
        try {
            return Double.valueOf(dStr);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private double readDouble(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            return 0.0;
        }
        return d;
    }

    public ThreeDayFoodLogAnalysis deduceThreeDayFoodLogAnalysis(String openId, String date) {
        List<FoodLog> threeDayFoodLog = foodLogDao.getThreeDayFoodLog(openId, date);
        if (threeDayFoodLog.size() < 3) {
            logger.info("Food log is less than 3 days, fail to analytic.");
            return new ThreeDayFoodLogAnalysis();
        }
        ThreeDayFoodLogAnalysis analysis = new ThreeDayFoodLogAnalysis();
        boolean isStandardLogType = isStandardLogType(threeDayFoodLog);

        //非标返回空
        if (!isStandardLogType) {
            return new ThreeDayFoodLogAnalysis();
        }

        analysis.setStandardLog(Boolean.TRUE);
        Map<String, Double> elementEvgs = deduceElementEvgs(threeDayFoodLog);
        analysis.setElementEvgs(elementEvgs);
        if (userDao.getUserByOpenId(openId) != null) {
            analysis.setDieticianAdvice(deduceDieticianAdvice(threeDayFoodLog, elementEvgs, openId));
        }
        return analysis;
    }

    public Map<String, Double> deduceElementEvgs(List<FoodLog> foodLogList) {
        logger.info("Deducing elements average.");
        double ca = 0.0d;
        double cho = 0.0d;
        double fat = 0.0d;
        double k = 0.0d;
        double p = 0.0d;
        double na = 0.0d;
        double protein = 0.0;
        double energy = 0.0;
        double ceRatio = 0.0;
        double feRatio = 0.0;
        double peRatio = 0.0;
        for (FoodLog foodLog : foodLogList) {
            ca += foodLog.getCa();
            cho += foodLog.getCho();
            fat += foodLog.getFat();
            k += foodLog.getK();
            p += foodLog.getP();
            na += foodLog.getNa();
            protein += foodLog.getTotalProtein();
            energy += foodLog.getTotalEnergy();
            ceRatio += foodLog.getCeRatio();
            feRatio += foodLog.getFeRatio();
            peRatio += foodLog.getTotalProtein() * foodLog.getPeRatio();
        }

        return ImmutableMap.<String, Double>builder()
                .put("ca", BigDecimal.valueOf(ca / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("cho", BigDecimal.valueOf(cho / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("fat", BigDecimal.valueOf(fat / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("k", BigDecimal.valueOf(k / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("p", BigDecimal.valueOf(p / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("na", BigDecimal.valueOf(na / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("totalProtein", BigDecimal.valueOf(protein / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("totalEnergy", BigDecimal.valueOf(energy / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("ceRatio", BigDecimal.valueOf(ceRatio / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("feRatio", BigDecimal.valueOf(feRatio / 3).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .put("peRatio", BigDecimal.valueOf(peRatio / protein).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue())
                .build();
    }

    //Standard Log Type: continuous 3 days, including 1 weekend(Saturday or Sunday)
    public boolean isStandardLogType(List<FoodLog> foodLogList) {
        Date dateOne = foodLogList.get(0).getDate();
        Date dateTwo = foodLogList.get(1).getDate();
        Date dateThree = foodLogList.get(2).getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOne);
        return (int) ((dateOne.getTime() - dateTwo.getTime()) / ONE_DAY_MILI_SECONDS) == 1 &&
                (int) ((dateTwo.getTime() - dateThree.getTime()) / ONE_DAY_MILI_SECONDS) == 1 &&
                (cal.get(Calendar.DAY_OF_WEEK) == 3 || cal.get(Calendar.DAY_OF_WEEK) == 7);
    }

    public boolean isStandardLogType(Date dateOne, Date dateTwo, Date dateThree) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOne);
        return (int) ((dateOne.getTime() - dateTwo.getTime()) / ONE_DAY_MILI_SECONDS) == 1 &&
                (int) ((dateTwo.getTime() - dateThree.getTime()) / ONE_DAY_MILI_SECONDS) == 1 &&
                (cal.get(Calendar.DAY_OF_WEEK) == 3 || cal.get(Calendar.DAY_OF_WEEK) == 7);
    }


    public List<String> deduceDieticianAdvice(List<FoodLog> foodLogList, Map<String, Double> elementAvgs, String openId) {
        User user = userDao.getUserByOpenId(openId);
        double energy = elementAvgs.get("totalEnergy");
        double protein = elementAvgs.get("totalProtein");
        double na = elementAvgs.get("na");
        double ca = elementAvgs.get("ca");
        double p = elementAvgs.get("p");
        double ceRatio = elementAvgs.get("ceRatio");
        double feRatio = elementAvgs.get("feRatio");
        double peRatio = elementAvgs.get("peRatio");

        float standardCalorie = calCalorie(user);
        String[] standardProtein = calProtein(user).split("~");

        List<String> dieticianAdvice = new ArrayList<String>();

        if (energy - standardCalorie >= 100 || standardCalorie - energy <= 100) {
            dieticianAdvice.add("您的能量摄入基本接近推荐值。");
        } /*else if (energy - standardCalorie > 100 && energy - standardCalorie <= 200) {
            dieticianAdvice.add("您的能量摄入略高于推荐值。");
        } else if (standardCalorie - energy > 100 && standardCalorie - energy <= 200) {
            dieticianAdvice.add("您的能量摄入略低于推荐值。");
        }*/ else if (energy - standardCalorie > 200) {
            dieticianAdvice.add("您的能量摄入高于推荐值。");
        } else if (standardCalorie - energy > 200) {
            dieticianAdvice.add("您的能量摄入低于推荐值。");
        }

        if (protein - Double.valueOf(standardProtein[1]) <= 3 && Double.valueOf(standardProtein[0]) - protein <= 3) {
            dieticianAdvice.add("蛋白质摄入量基本接近推荐值。");
        } else if (protein - Double.valueOf(standardProtein[1]) > 3 &&  protein - Double.valueOf(standardProtein[1]) < 5) {
            dieticianAdvice.add("蛋白质摄入量略高于推荐值。");
        }  else if (protein - Double.valueOf(standardProtein[1]) > 5) {
            dieticianAdvice.add("蛋白质摄入量高于推荐值。");
        } else if (Double.valueOf(standardProtein[0]) - protein > 3 && Double.valueOf(standardProtein[0]) - protein < 5) {
            dieticianAdvice.add("蛋白质摄入量略低于推荐值。");
        } else if (Double.valueOf(standardProtein[0]) - protein > 5) {
            dieticianAdvice.add("蛋白质摄入量低于推荐值。");
        }

        if (peRatio < 0.5) {
            dieticianAdvice.add("优质蛋白质比例偏低，应增加蛋奶肉鱼或大豆类。");
        } else if (peRatio >= 0.5 && peRatio <= 0.70) {
            dieticianAdvice.add("优质蛋白质比例合理。");
        } else {
            dieticianAdvice.add("优质蛋白质比例较高。");
        }

        if (feRatio < 0.25) {
            dieticianAdvice.add("脂肪摄入量比推荐值少，可以适量增加烹调用油。");
        } else if (feRatio >= 0.25 && feRatio <= 0.35) {
            dieticianAdvice.add("脂肪摄入的比例较合理。");
        } else {
            dieticianAdvice.add("脂肪摄入量比推荐值多，可以适当减少烹调用油。");
        }

        if (ceRatio < 0.55) {
            dieticianAdvice.add("碳水化合物摄入量比推荐值少，可以适量增加淀粉类食物。");
        } else if (ceRatio >= 0.55 && ceRatio <= 0.65) {
            dieticianAdvice.add("碳水化合物摄入的比例较合理。");
        } else {
            dieticianAdvice.add("碳水化合物摄入量比推荐值多，可以适当减少淀粉类食物。");
        }

        if (na > 2000) {
            dieticianAdvice.add("慢性肾脏病患者钠摄入量应低于2000 mg/d。盐、腌制的食物、酱油及各种酱料的钠含量很高，尽量减少摄入量。");
        }
        dieticianAdvice.add("若您血钾高，尽量减少含钾高的绿叶蔬菜、土豆等食物。");
        if (ca >= 2000) {
            dieticianAdvice.add("钙摄入量偏多，适当减少含钙高的奶类、大豆制品等。");
        } else {
            dieticianAdvice.add("慢性肾脏病，钙摄入量应低于2000 mg/d。");
        }
        if (p >= 800) {
            dieticianAdvice.add("慢性肾脏病，磷摄入量应低于800mg/d。尽量选择低磷食物。");
        } else {
            dieticianAdvice.add("慢性肾脏病，磷摄入量应低于800mg/d，若血磷高，应尽可能减低磷的摄入。");
        }
        return dieticianAdvice;


    }

    private float calCalorie(User user) {
        String sportRate = user.getSportRate();
        float standardWeight = calStandardWeight(user);
        float bmi = calBmiIndex(user);
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

    private float calBmiIndex(User user) {
        String height = user.getHeight();
        String weight = user.getWeight();
        return (Float.parseFloat(getHeightOrWeight(weight)) / (Float.parseFloat(getHeightOrWeight(height)) / 100))
                / (Float.parseFloat(getHeightOrWeight(height)) / 100);
    }

    private String calProtein(User user) {
        int nephroticPeriod = Integer.parseInt(user.getNephroticPeriod());
        List<String> treatmentMethod = Arrays.asList(user.getTreatmentMethod().split(","));
        BigDecimal standardWeight = BigDecimal.valueOf(calStandardWeight(user));
        StringBuilder protein = new StringBuilder();
        if (nephroticPeriod >= 1 && nephroticPeriod <= 2) {
            return protein.append(standardWeight.multiply(BigDecimal.valueOf(1)).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                    .append(standardWeight.multiply(BigDecimal.ONE).setScale(1, BigDecimal.ROUND_FLOOR)).toString();
        } else if (nephroticPeriod == 3) {
            if (treatmentMethod.stream().anyMatch(item -> item.contains("dialysis"))) {
                return protein.append(standardWeight.multiply(BigDecimal.ONE).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                        .append(standardWeight.multiply(BigDecimal.valueOf(1.2)).setScale(1, BigDecimal.ROUND_FLOOR)).toString();
            } else {
                return protein.append(standardWeight.multiply(BigDecimal.valueOf(0.8)).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                        .append(standardWeight.multiply(BigDecimal.valueOf(10.8)).setScale(1, BigDecimal.ROUND_FLOOR)).toString();
            }
        } else if (nephroticPeriod > 3 && nephroticPeriod <= 5) {
            if (treatmentMethod.stream().anyMatch(item -> item.contains("dialysis"))) {
                return protein.append(standardWeight.multiply(BigDecimal.ONE).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                        .append(standardWeight.multiply(BigDecimal.valueOf(1.2)).setScale(1, BigDecimal.ROUND_FLOOR)).toString();
            } else {
                return protein.append(standardWeight.multiply(BigDecimal.valueOf(0.6)).setScale(1, BigDecimal.ROUND_FLOOR)).append("~")
                        .append(standardWeight.multiply(BigDecimal.valueOf(10.8)).setScale(1, BigDecimal.ROUND_FLOOR)).toString();
            }
        }
        return "Protein is unclear.";
    }

}
