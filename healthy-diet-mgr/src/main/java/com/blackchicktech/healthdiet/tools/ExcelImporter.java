package com.blackchicktech.healthdiet.tools;

import com.blackchicktech.healthdiet.entity.FoodTbl;
import com.blackchicktech.healthdiet.entity.FoodWeight;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExcelImporter {

    private static String filePath = "/Users/quan/Documents/Apps/diet.xlsx";
    private static int totalRows = 2054;  //后面是有格式的脏数据

    public static void main(String[] args) throws Exception {
        List<FoodTbl> foodTbls = new ArrayList<>();
        List<FoodWeight> foodWeights = new ArrayList<>();

        File file = new File(filePath);
        XSSFWorkbook workbook = null;
        try {
            System.out.println("Begin to load workbook " + file.getName());
            workbook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet1 = workbook.getSheetAt(0);
            System.out.println("Read sheet " + sheet1.getSheetName() + " total " + totalRows + " rows");
            //row 0 是header
            for (int i = 3; i < totalRows; i++) {
                XSSFRow row = sheet1.getRow(i);
                FoodTbl foodTbl = new FoodTbl();
                foodTbl.setFoodCode(readCellAsIntString(row.getCell(0))); //类编码
                foodTbl.setSubCode(readCellAsIntString(row.getCell(2)));
                foodTbl.setSubName(readCellAsString(row.getCell(3)));
                foodTbl.setFoodAlias(readCellAsString(row.getCell(4)));
                foodTbl.setFoodId(readCellAsString(row.getCell(5)));
                foodTbl.setFoodName(readCellAsString(row.getCell(6)));
                foodTbl.setWater(readCellAsString(row.getCell(8)));
                foodTbl.setEnergy(readCellAsDouble(row.getCell(9)));
                foodTbl.setProtein(readCellAsDouble(row.getCell(11)));
                foodTbl.setFat(readCellAsString(row.getCell(13)));
                foodTbl.setCho(readCellAsString(row.getCell(15)));
                foodTbl.setCa(readCellAsString(row.getCell(35)));
                foodTbl.setP(readCellAsString(row.getCell(36)));
                foodTbl.setK(readCellAsString(row.getCell(37)));
                foodTbl.setNa(readCellAsString(row.getCell(39)));
                foodTbl.setUnit(readCellAsString(row.getCell(60)));
                foodTbl.setEdible(readAsInt(row.getCell(7), 0));
                foodTbls.add(foodTbl);

                FoodWeight foodWeight = new FoodWeight();
                foodWeight.setFoodId(readCellAsString(row.getCell(5)));
                foodWeight.setFoodCode(readCellAsIntString(row.getCell(0))); //类编码
                foodWeight.setSubCode(readCellAsIntString(row.getCell(2)));
                foodWeight.setProteinWeight(readAsInt(row.getCell(12)));
                foodWeight.setFatWeight(readAsInt(row.getCell(14)));
                foodWeight.setChoWeight(readAsInt(row.getCell(17)));
//                foodWeight.setkWeight(readAsInt(row.getCell(37)));
                foodWeight.setNaWeight(readAsInt(row.getCell(40)));
//                foodWeight.setpWeight(readAsInt(row.getCell(58)));
                foodWeight.setCholesterolWeight(readAsInt(row.getCell(20)));
                foodWeight.setPurineWeight(readAsInt(row.getCell(23)));
                foodWeights.add(foodWeight);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Finished to load workbook " + file.getName());
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    //by pass
                }
            }
        }

        saveFoodTblToCsv(foodTbls);
        saveFoodWeightToCsv(foodWeights);
    }

    private static void saveFoodTblToCsv(List<FoodTbl> foodTbls) throws IOException {
        foodTbls.forEach(System.out::println);
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("./food_tbl.csv"));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("food_code", "sub_code", "sub_name", "food_id", "food_alias", "food_name", "water", "energy", "protein", "fat", "cho", "cholesterol", "ca", "p", "k", "na", "unit", "edible"))
        ) {
            for (FoodTbl foodTbl : foodTbls) {
                csvPrinter.printRecord(foodTbl.getFoodCode(),
                        foodTbl.getSubCode(),
                        foodTbl.getSubName(),
                        foodTbl.getFoodId(),
                        foodTbl.getFoodAlias(),
                        foodTbl.getFoodName(),
                        foodTbl.getWater(),
                        foodTbl.getEnergy(),
                        foodTbl.getProtein(),
                        foodTbl.getFat(),
                        foodTbl.getCho(),
                        foodTbl.getCholesterol(),
                        foodTbl.getCa(),
                        foodTbl.getP(),
                        foodTbl.getK(),
                        foodTbl.getNa(),
                        foodTbl.getUnit(),
                        foodTbl.getEdible()
                );
            }
            csvPrinter.flush();
        }
    }

    private static void saveFoodWeightToCsv(List<FoodWeight> foodWeights) throws IOException {
        foodWeights.forEach(System.out::println);
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("./food_weight_tbl.csv"));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("food_id", "food_code", "sub_code", "protein_weight", "fat_weight", "cho_weight", "na_weight", "cholesterol_weight", "purine_weight"))
        ) {
            for (FoodWeight foodWeight : foodWeights) {
                csvPrinter.printRecord(foodWeight.getFoodId(),
                        foodWeight.getFoodCode(),
                        foodWeight.getSubCode(),
                        foodWeight.getProteinWeight(),
                        foodWeight.getFatWeight(),
                        foodWeight.getChoWeight(),
//                        foodWeight.getkWeight(),
                        foodWeight.getNaWeight(),
                        foodWeight.getCholesterolWeight(),
                        foodWeight.getPurineWeight()
//                        foodWeight.getpWeight()
                );
            }
            csvPrinter.flush();
        }
    }

    public static String readCellAsString(XSSFCell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return "" + cell.getNumericCellValue();
            case BLANK:
                return null;
            default:
                throw new RuntimeException("Not supported cell type");
        }
    }

    // read number field 1.0 as string 1
    public static String readCellAsIntString(XSSFCell cell) {
        if (cell == null) {
            throw new RuntimeException("Id could not be null");
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                throw new RuntimeException("Not supported cell type");
        }
    }

    public static int readAsInt(XSSFCell cell) {
        if (cell == null) {
            throw new RuntimeException("Int could not be null");
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return Integer.parseInt(cell.getStringCellValue());
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            default:
                System.out.println("Not supported cell type " + cell.getCellTypeEnum().name());
                return 9999;
        }
    }

    public static int readAsInt(XSSFCell cell, int defultValue) {
        if (cell == null) {
            throw new RuntimeException("Int could not be null");
        }
        try {
            return readAsInt(cell);
        } catch (Exception e) {
            return defultValue;
        }
    }

    public static double readCellAsDouble(XSSFCell cell) {
        if (cell == null) {
            throw new RuntimeException("Double could not be null");
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return parseDataToDouble(cell.getStringCellValue());
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                return 0.0;
        }
    }

    public static float readCellAsFloat(XSSFCell cell) {
        if (cell == null) {
            throw new RuntimeException("Float could not be null");
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return Float.valueOf(cell.getStringCellValue());
            case NUMERIC:
                return (float)cell.getNumericCellValue();
            default:
                return 0.0f;
        }
    }

    private static double parseDataToDouble(String rawData) {
        if (rawData.contains("*")) {
            return Double.parseDouble(rawData.replace("*",""));
        } else {
            try {
                return Double.parseDouble(rawData);
            } catch (Exception e) {
                return 0.0;
            }
        }
    }
}
