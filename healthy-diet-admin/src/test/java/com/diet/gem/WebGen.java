package com.diet.gem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LiuYu
 */
public class WebGen {
    /**
     * 功能：生成Dao类主体代码
     */
    public static void parse(String tableName) {
        String webName = GenTool.initcap(tableName) + "Controller";
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.webPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import com.alibaba.fastjson.JSONObject;\r\n");
        sb.append("import com.diet.core.base.BaseController;\r\n");
        sb.append("import " + GenTool.entityPackageOutPath + "." + GenTool.initcap(tableName) + ";\r\n");
        sb.append("import com.diet.message.ResponseMsg;\r\n");
        sb.append("import " + GenTool.svcPackageOutPath + ".I" + GenTool.initcap(tableName) + "Service;\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
        sb.append("import org.springframework.data.domain.Page;\r\n");
        sb.append("import org.springframework.data.domain.PageRequest;\r\n");
        sb.append("import org.springframework.data.domain.Pageable;\r\n");
        sb.append("import org.springframework.web.bind.annotation.*;\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@RestController\r\n");
        sb.append("@RequestMapping(BaseController.API + \"/" + GenTool.initcap2(tableName) + "\")\r\n");

        //实体部分
        sb.append("public class " + webName + " extends BaseController {\r\n\r\n");
        sb.append("\t@Autowired\r\n");
        sb.append("\tprivate I" + GenTool.initcap(tableName) + "Service " + GenTool.initcap2(tableName) + "Service;\r\n\r\n");

        sb.append("\t@PostMapping(\"/save\")\r\n");
        sb.append("\tpublic ResponseMsg insert(@RequestBody " + GenTool.initcap(tableName) + " model) {\r\n");
        sb.append("\t\tResponseMsg responseMsg = new ResponseMsg();\r\n");
        sb.append("\t\t" + GenTool.initcap(tableName) + " result = " + GenTool.initcap2(tableName) + "Service.save(model);\r\n");
        sb.append("\t\tresponseMsg.setData(result);\r\n");
        sb.append("\t\treturn responseMsg;\r\n");
        sb.append("\t}\r\n\r\n");

        sb.append("\t@PostMapping(\"/deleteById/{id}\")\r\n");
        sb.append("\tpublic ResponseMsg deleteById(@PathVariable Integer id) {\r\n");
        sb.append("\t\tResponseMsg responseMsg = new ResponseMsg();\r\n");
        sb.append("\t\t" + GenTool.initcap2(tableName) + "Service.deleteById(id);\r\n");
        sb.append("\t\treturn responseMsg;\r\n");
        sb.append("\t}\r\n\r\n");

        sb.append("\t@PostMapping(\"/selectById/{id}\")\r\n");
        sb.append("\tpublic ResponseMsg selectById(@PathVariable Integer id) {\r\n");
        sb.append("\t\tResponseMsg responseMsg = new ResponseMsg();\r\n");
        sb.append("\t\t" + GenTool.initcap(tableName) + " result = " + GenTool.initcap2(tableName) + "Service.findById(id);\r\n");
        sb.append("\t\tresponseMsg.setData(result);\r\n");
        sb.append("\t\treturn responseMsg;\r\n");
        sb.append("\t}\r\n\r\n");

        sb.append("\t@PostMapping(\"/list\")\r\n");
        sb.append("\tpublic ResponseMsg list(@RequestBody JSONObject request) {\r\n");
        sb.append("\t\tResponseMsg responseMsg = new ResponseMsg();\r\n");
        sb.append("\t\tInteger pageNum = request.getInteger(\"pageNum\");\r\n");
        sb.append("\t\tInteger pageSize = request.getInteger(\"pageSize\");\r\n");
        sb.append("\t\tif(pageNum == null){\r\n");
        sb.append("\t\t\tpageNum = 0;\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t\tif(pageSize == null){\r\n");
        sb.append("\t\t\tpageSize = 10;\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t\tPageable pageable = PageRequest.of(pageNum, pageSize);\r\n");
        sb.append("\t\tPage<" + GenTool.initcap(tableName) + "> result = " + GenTool.initcap2(tableName) + "Service.findAll(pageable);\r\n");
        sb.append("\t\tresponseMsg.setData(result);\r\n");
        sb.append("\t\treturn responseMsg;\r\n");
        sb.append("\t}\r\n\r\n");
        sb.append("}");

        try {
            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];
            String outputPath = path + "/src/main/java/" + GenTool.webPackageOutPath.replace(".", "/");
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + "/" + webName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("生成Controller: " + webName + ".java");
    }
}
