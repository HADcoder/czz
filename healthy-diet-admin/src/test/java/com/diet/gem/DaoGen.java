package com.diet.gem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LiuYu
 * @date 2017/12/5
 */
public class DaoGen {
    /**
     * 功能：生成Dao类主体代码
     */
    public static void parse(String tableName) {
        String daoName = GenTool.initcap(tableName) + "Dao";
        String idType = "Object";
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.daoPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import com.diet.core.base.BaseDao;\r\n");
        sb.append("import " + GenTool.entityPackageOutPath + "." + GenTool.initcap(tableName) + ";\r\n");
        sb.append("import org.springframework.stereotype.Repository;\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@Repository\r\n");

        //实体部分
        sb.append("public interface " + daoName + " extends BaseDao<" + GenTool.initcap(tableName) + ", " + idType + "> {\r\n\r\n");
        sb.append("}\r\n");

        try {
            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];
            String outputPath = path + "/src/main/java/" + GenTool.daoPackageOutPath.replace(".", "/");
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + "/" + daoName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("生成Dao: " + daoName + ".java");
    }
}
