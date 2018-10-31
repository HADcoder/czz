package com.diet.gem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LiuYu
 */
public class ServiceGen {
    /**
     * 功能：生成Service主体代码
     */
    public static void parse(String tableName) {
        String svcName = "I" + GenTool.initcap(tableName) + "Service";
        String svcImplName = GenTool.initcap(tableName) + "ServiceImpl";
        genSvc(tableName, svcName);
        genSvcImpl(tableName, svcName, svcImplName);
    }

    /**
     * 生成Service接口
     *
     * @param svcName
     */
    private static void genSvc(String tableName, String svcName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.svcPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import com.diet.core.base.BaseService;\r\n");
        sb.append("import " + GenTool.entityPackageOutPath + "." + GenTool.initcap(tableName) + ";\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        //实体部分
        sb.append("public interface " + svcName + " extends BaseService<" + GenTool.initcap(tableName) + "> {\r\n\r\n");
        sb.append("}\r\n");

        try {
            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];
            String outputPath = path + "/src/main/java/" + GenTool.svcPackageOutPath.replace(".", "/");
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + "/" + svcName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("生成Service: " + svcName + ".java");
    }

    /**
     * 生成Service实现类
     *
     * @param svcName
     * @param svcImplName
     */
    private static void genSvcImpl(String tableName, String svcName, String svcImplName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.svcImplPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import com.diet.core.base.impl.BaseServiceImpl;\r\n");
        sb.append("import " + GenTool.entityPackageOutPath + "." + GenTool.initcap(tableName) + ";\r\n");
        sb.append("import " + GenTool.svcPackageOutPath + "." + svcName + ";\r\n");
        sb.append("import org.springframework.stereotype.Service;\r\n\r\n");


        //注释部分
        sb.append("/**\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@Service\r\n");
        //实体部分
        sb.append("public class " + svcImplName + " extends BaseServiceImpl<" + GenTool.initcap(tableName) + "> implements " + svcName + " {\r\n\r\n");
        sb.append("}\r\n");

        try {
            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];
            String outputPath = path + "/src/main/java/" + GenTool.svcImplPackageOutPath.replace(".", "/") + "/";
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + svcImplName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("生成ServiceImpl: " + svcImplName + ".java");
    }
}
