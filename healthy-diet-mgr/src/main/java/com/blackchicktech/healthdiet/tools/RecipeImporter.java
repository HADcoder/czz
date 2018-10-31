package com.blackchicktech.healthdiet.tools;

import com.blackchicktech.healthdiet.entity.Recipe;
import com.blackchicktech.healthdiet.entity.RecipeWeight;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.blackchicktech.healthdiet.tools.ExcelImporter.*;

public class RecipeImporter {

    private static String filePath = "/Users/quan/Documents/Apps/recipe.xlsx";
    private static int totalRows = 57; //后面是有格式的脏数据

    public static void main(String[] args) throws Exception {
        excelImporter();
    }

    private static void excelImporter() throws IOException {

        List<Recipe> recipeList = new ArrayList<>();

        List<RecipeWeight> recipeWeightList = new ArrayList<>();

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
                Recipe recipe = new Recipe();
                recipe.setRecipeId(readCellAsIntString(row.getCell(0)));
                recipe.setRecipeName(readCellAsString(row.getCell(1)));
                recipe.setCookMethod(readCellAsString(row.getCell(2)));
                recipe.setTaste(readCellAsString(row.getCell(3)));
                recipe.setCuisine(readCellAsString(row.getCell(4)));
                recipe.setAgeGroup(readCellAsString(row.getCell(5)));
                recipe.setDifficulty(readCellAsString(row.getCell(6)));
                recipe.setPrepareTime(readAsInt(row.getCell(7)));
                recipe.setCookingTime(readAsInt(row.getCell(8)));
                recipe.setMealTime(readCellAsString(row.getCell(9)));
                recipe.setCategory(readCellAsString(row.getCell(10)));
                recipe.setMaterial(readCellAsString(row.getCell(11)));
                recipe.setMainIngredients(readCellAsString(row.getCell(12)));
                recipe.setSupplementary(readCellAsString(row.getCell(13)));
                recipe.setEnergy(readCellAsFloat(row.getCell(14)));
                recipe.setProtein(readCellAsFloat(row.getCell(15)));
                recipe.setCkdCategory(readCellAsString(row.getCell(24)));
                recipe.setCookingnote(readCellAsString(row.getCell(25)));
                recipeList.add(recipe);

                RecipeWeight recipeWeight = new RecipeWeight();
                recipeWeight.setRecipeId(readCellAsIntString(row.getCell(0)));
                recipeWeight.setMaterial(readCellAsString(row.getCell(11)));
                recipeWeight.setProteinWeight(readAsInt(row.getCell(16)));
                recipeWeight.setFatWeight(readAsInt(row.getCell(17)));
                recipeWeight.setChoWeight(readAsInt(row.getCell(18)));
                recipeWeight.setCholesterolWeight(readAsInt(row.getCell(19)));
                recipeWeight.setPurineWeight(readAsInt(row.getCell(20)));
                recipeWeight.setkWeight(readAsInt(row.getCell(21)));
                recipeWeight.setNaWeight(readAsInt(row.getCell(22)));
                recipeWeightList.add(recipeWeight);
            }

            System.out.println("Totally " + totalRows + "read.");
            saveRecipeTblToCsv(recipeList);
            saveRecipeWeightTblToCsv(recipeWeightList);

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


    }

    private static void saveRecipeTblToCsv(List<Recipe> recipeList) throws IOException {
        recipeList.forEach(System.out::println);
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("./recipe_tbl.csv"));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("recipe_id", "recipe_name", "cook_method", "taste", "cuisine", "age_group", "difficulty", "prepare_time", "cooking_time", "meal_time",
                                "category", "material", "main_ingredients", "supplementary", "cookingnote",
                                "energy", "protein", "ckd_category"))
        ) {
            for (Recipe recipe : recipeList) {
                csvPrinter.printRecord(recipe.getRecipeId(),
                        recipe.getRecipeName(),
                        recipe.getCookMethod(),
                        recipe.getTaste(),
                        recipe.getCuisine(),
                        recipe.getAgeGroup(),
                        recipe.getDifficulty(),
                        recipe.getPrepareTime(),
                        recipe.getCookingTime(),
                        recipe.getMealTime(),
                        recipe.getCategory(),
                        recipe.getMaterial(),
                        recipe.getMainIngredients(),
                        recipe.getSupplementary(),
                        recipe.getCookingnote(),
                        recipe.getEnergy(),
                        recipe.getProtein(),
                        recipe.getCkdCategory()
                );
            }
            csvPrinter.flush();
        }
    }

    private static void saveRecipeWeightTblToCsv(List<RecipeWeight> recipeWeightList) throws IOException {
        recipeWeightList.forEach(System.out::println);
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("./recipe_weight_tbl.csv"));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("recipe_id", "material", "protein_weight", "fat_weight", "cho_weight", "na_weight", "cholesterol_weight", "purine_weight", "k_weight"))
        ) {
            for (RecipeWeight recipeWeight : recipeWeightList) {
                csvPrinter.printRecord(recipeWeight.getRecipeId(),
                        recipeWeight.getMaterial(),
                        recipeWeight.getProteinWeight(),
                        recipeWeight.getFatWeight(),
                        recipeWeight.getChoWeight(),
                        recipeWeight.getNaWeight(),
                        recipeWeight.getCholesterolWeight(),
                        recipeWeight.getPurineWeight(),
                        recipeWeight.getkWeight()
                );
            }
            csvPrinter.flush();
        }
    }
}
