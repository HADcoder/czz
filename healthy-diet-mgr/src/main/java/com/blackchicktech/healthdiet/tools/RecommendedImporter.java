package com.blackchicktech.healthdiet.tools;

import com.blackchicktech.healthdiet.entity.FoodRecommended;
import com.blackchicktech.healthdiet.entity.Recipe;
import com.blackchicktech.healthdiet.entity.RecipeWeight;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
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

import static com.blackchicktech.healthdiet.tools.ExcelImporter.*;

public class RecommendedImporter {

    private static String filePath = "/Users/quan/Documents/Apps/food_recommended.xlsx";
    private static int totalRows = 20; //后面是有格式的脏数据

    public static void main(String[] args) throws Exception {
        excelImporter();
    }

    private static void excelImporter() throws IOException {

        List<FoodRecommended> foodRecommendedList = new ArrayList<>();

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
                FoodRecommended foodRecommended = new FoodRecommended();
                String ckd  = readCellAsString(row.getCell(0));
                if (ckd  == null) {
                    continue;
                }
                foodRecommended.setCdkPeriod(ckd);
                foodRecommended.setWeight(readAsInt(row.getCell(1)));
                foodRecommended.setEnergy(readCellAsDouble(row.getCell(2)));
                foodRecommended.setProtein(readCellAsDouble(row.getCell(3)));

                foodRecommended.setGrainsBR(readCellAsDouble(row.getCell(4)));
                foodRecommended.setGrainsBP(readCellAsDouble(row.getCell(5)));
                foodRecommended.setGrainsLR(readCellAsDouble(row.getCell(6)));
                foodRecommended.setGrainsLP(readCellAsDouble(row.getCell(7)));
                foodRecommended.setGrainsDR(readCellAsDouble(row.getCell(8)));
                foodRecommended.setGrainsDP(readCellAsDouble(row.getCell(9)));

                foodRecommended.setStarchBR(readCellAsDouble(row.getCell(10)));
                foodRecommended.setStarchBP(readCellAsDouble(row.getCell(11)));
                foodRecommended.setStarchLR(readCellAsDouble(row.getCell(12)));
                foodRecommended.setStarchLP(readCellAsDouble(row.getCell(13)));
                foodRecommended.setStarchDR(readCellAsDouble(row.getCell(14)));
                foodRecommended.setStarchDP(readCellAsDouble(row.getCell(15)));

                foodRecommended.setWegeBR(readCellAsDouble(row.getCell(16)));
                foodRecommended.setWegeBP(readCellAsDouble(row.getCell(17)));
                foodRecommended.setWegeLR(readCellAsDouble(row.getCell(18)));
                foodRecommended.setWegeLP(readCellAsDouble(row.getCell(19)));
                foodRecommended.setWegeDR(readCellAsDouble(row.getCell(20)));
                foodRecommended.setWegeDP(readCellAsDouble(row.getCell(21)));

                foodRecommended.setWegeFruitBR(readCellAsDouble(row.getCell(22)));
                foodRecommended.setWegeFruitBP(readCellAsDouble(row.getCell(23)));
                foodRecommended.setWegeFruitLR(readCellAsDouble(row.getCell(24)));
                foodRecommended.setWegeFruitLP(readCellAsDouble(row.getCell(25)));
                foodRecommended.setWegeFruitDR(readCellAsDouble(row.getCell(26)));
                foodRecommended.setWegeFruitDP(readCellAsDouble(row.getCell(27)));

                foodRecommended.setMilkBR(readCellAsDouble(row.getCell(28)));
                foodRecommended.setMilkBP(readCellAsDouble(row.getCell(29)));
                foodRecommended.setMilkLR(readCellAsDouble(row.getCell(30)));
                foodRecommended.setMilkLP(readCellAsDouble(row.getCell(31)));
                foodRecommended.setMilkDR(readCellAsDouble(row.getCell(32)));
                foodRecommended.setMilkDP(readCellAsDouble(row.getCell(33)));

                foodRecommended.setEggsBR(readCellAsDouble(row.getCell(34)));
                foodRecommended.setEggsBP(readCellAsDouble(row.getCell(35)));
                foodRecommended.setEggsLR(readCellAsDouble(row.getCell(36)));
                foodRecommended.setEggsLP(readCellAsDouble(row.getCell(37)));
                foodRecommended.setEggsDR(readCellAsDouble(row.getCell(38)));
                foodRecommended.setEggsDP(readCellAsDouble(row.getCell(39)));

                foodRecommended.setFatBR(readCellAsDouble(row.getCell(40)));
                foodRecommended.setFatBP(readCellAsDouble(row.getCell(41)));
                foodRecommended.setFatLR(readCellAsDouble(row.getCell(42)));
                foodRecommended.setFatLP(readCellAsDouble(row.getCell(43)));
                foodRecommended.setFatDR(readCellAsDouble(row.getCell(44)));
                foodRecommended.setFatDP(readCellAsDouble(row.getCell(45)));

                foodRecommended.setFruitAR(readCellAsDouble(row.getCell(46)));
                foodRecommended.setFruitAP(readCellAsDouble(row.getCell(47)));

                foodRecommendedList.add(foodRecommended);
            }

            System.out.println("Totally " + totalRows + "read.");
            saveToCsv(foodRecommendedList);

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

    private static void saveToCsv(List<FoodRecommended> foodRecommendedList) throws IOException {
        foodRecommendedList.forEach(System.out::println);
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("./food_recommended_tbl.csv"));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("cdk_period",
                                "weight",
                                "energy",
                                "protein",

                                "grains_b_r",
                                "grains_b_p",
                                "grains_l_r",
                                "grains_l_p",
                                "grains_d_r",
                                "grains_d_p",

                                "starch_b_r",
                                "starch_b_p",
                                "starch_l_r",
                                "starch_l_p",
                                "starch_d_r",
                                "starch_d_p",

                                "wege_b_r",
                                "wege_b_p",
                                "wege_l_r",
                                "wege_l_p",
                                "wege_d_r",
                                "wege_d_p",

                                "wege_fruit_b_r",
                                "wege_fruit_b_p",
                                "wege_fruit_l_r",
                                "wege_fruit_l_p",
                                "wege_fruit_d_r",
                                "wege_fruit_d_p",

                                "milk_b_r",
                                "milk_b_p",
                                "milk_l_r",
                                "milk_l_p",
                                "milk_d_r",
                                "milk_d_p",

                                "eggs_b_r",
                                "eggs_b_p",
                                "eggs_l_r",
                                "eggs_l_p",
                                "eggs_d_r",
                                "eggs_d_p",

                                "fat_b_r",
                                "fat_b_p",
                                "fat_l_r",
                                "fat_l_p",
                                "fat_d_r",
                                "fat_d_p",

                                "fruit_a_r",
                                "fruit_a_p"

                        ))
        ) {
            for (FoodRecommended foodRecommended : foodRecommendedList) {
                csvPrinter.printRecord(
                        foodRecommended.getCdkPeriod(),
                        foodRecommended.getWeight(),
                        foodRecommended.getEnergy(),
                        foodRecommended.getProtein(),

                        foodRecommended.getGrainsBR(),
                        foodRecommended.getGrainsBP(),
                        foodRecommended.getGrainsLR(),
                        foodRecommended.getGrainsLP(),
                        foodRecommended.getGrainsDR(),
                        foodRecommended.getGrainsDP(),

                        foodRecommended.getStarchBR(),
                        foodRecommended.getStarchBP(),
                        foodRecommended.getStarchLR(),
                        foodRecommended.getStarchLP(),
                        foodRecommended.getStarchDR(),
                        foodRecommended.getStarchDP(),

                        foodRecommended.getWegeBR(),
                        foodRecommended.getWegeBP(),
                        foodRecommended.getWegeLR(),
                        foodRecommended.getWegeLP(),
                        foodRecommended.getWegeDR(),
                        foodRecommended.getWegeDP(),

                        foodRecommended.getWegeFruitBR(),
                        foodRecommended.getWegeFruitBP(),
                        foodRecommended.getWegeFruitLR(),
                        foodRecommended.getWegeFruitLP(),
                        foodRecommended.getWegeFruitDR(),
                        foodRecommended.getWegeFruitDP(),

                        foodRecommended.getMilkBR(),
                        foodRecommended.getMilkBP(),
                        foodRecommended.getMilkLR(),
                        foodRecommended.getMilkLP(),
                        foodRecommended.getMilkDR(),
                        foodRecommended.getMilkDP(),

                        foodRecommended.getEggsBR(),
                        foodRecommended.getEggsBP(),
                        foodRecommended.getEggsLR(),
                        foodRecommended.getEggsLP(),
                        foodRecommended.getEggsDR(),
                        foodRecommended.getEggsDP(),

                        foodRecommended.getFatBR(),
                        foodRecommended.getFatBP(),
                        foodRecommended.getFatLR(),
                        foodRecommended.getFatLP(),
                        foodRecommended.getFatDR(),
                        foodRecommended.getFatDP(),

                        foodRecommended.getFruitAR(),
                        foodRecommended.getFruitAP()

                );
            }
            csvPrinter.flush();
        }
    }
}
