package com.diet.gem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author LiuYu
 */
public class GenTool {

    public static String entityPackageOutPath = "com.jeeboot.entity";//指定实体生成所在包的路径
    public static String daoPackageOutPath = "com.jeeboot.dao";//指定Dao所在包的路径
    public static String svcPackageOutPath = "com.jeeboot.service";//指定service接口所在包的路径
    public static String svcImplPackageOutPath = "com.jeeboot.service.impl";//指定service实现所在包的路径
    public static String webPackageOutPath = "com.jeeboot.controller";//指定Controller实现所在包的路径
    public static String authorName = "LiuYu";//作者名字

    //数据库连接
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jeeboot?characterEncoding=utf8";
    private static final String NAME = "root";
    private static final String PASS = "";
    private static final String SQL = "SELECT table_name AS tableName, column_name AS columnName, data_type AS dataType, " +
            "column_comment AS columnComment, column_key AS columnKey, extra AS extra FROM information_schema.columns " +
            "WHERE table_schema ='jeeboot' AND table_name LIKE '%s' ORDER BY table_name ASC, ordinal_position ASC;";

    public static void main(String[] args) throws Exception {
//        entityPackageOutPath = "com.jeeboot.entity";
//        daoPackageOutPath = "com.jeeboot.dao";
//        svcPackageOutPath = "com.jeeboot.service";
//        svcImplPackageOutPath = "com.jeeboot.service.impl";
//        webPackageOutPath = "com.jeeboot.web";
        gen("tb_test%", true, true, true, true, "");
    }

    /**
     * 构造函数
     */
    public static void gen(String tbName, boolean isGenEntity, boolean isGenDao, boolean isGenService,
                           boolean isGenWeb, String prefix) {
        //创建连接
        Connection con = null;
        //查要生成实体类的表
        String sql = String.format(SQL, tbName);
        System.out.println(sql);
        PreparedStatement pStemt = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL, NAME, PASS);
            pStemt = con.prepareStatement(sql);
            ResultSet resultSet = pStemt.executeQuery();

            Map<String, List> columnMap = new HashMap<>();
            List<ColumnInfo> columnInfos = null;
            while (resultSet.next()) {
                if (columnMap.containsKey(resultSet.getString(1))) {
                    columnInfos = columnMap.get(resultSet.getString(1));
                    columnInfos.add(new ColumnInfo(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
                } else {
                    columnInfos = new ArrayList<>();
                    columnInfos.add(new ColumnInfo(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
                    columnMap.put(resultSet.getString(1), columnInfos);
                }
            }

            for (Map.Entry<String, List> map : columnMap.entrySet()) {
                String tableName = subStrByPrefix(map.getKey(), prefix);
                if (isGenEntity) {
                    EntityGen.parse(tableName, map.getValue());
                }
                if (isGenDao) {
                    DaoGen.parse(tableName);
                }
                if (isGenService) {
                    ServiceGen.parse(tableName);
                }
                if (isGenWeb) {
                    WebGen.parse(tableName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生成完毕！");
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public static String initcap(String str) {
        String[] arr = str.split("_");
        String result = "";
        if(arr.length == 1){
            return initcap0(str);
        }
        for (int i=0; i<arr.length; i++) {
            if(i == 0) {
                char[] ch = initcap0(arr[i].toLowerCase()).toCharArray();
                if (ch[0] >= 'a' && ch[0] <= 'z') {
                    ch[0] = (char) (ch[0] - 32);
                }
                result += new String(ch);
            } else {
                result += initcap0(arr[i].toLowerCase());
            }
        }
        return result;
    }

    /**
     * 功能：将输入字符串的首字母改成小写
     *
     * @param str
     * @return
     */
    public static String initcap2(String str) {
        String[] arr = str.split("_");
        String result = "";
        if(arr.length == 1){
            return initcap1(str);
        }
        for (int i=0; i<arr.length; i++) {
            if(i == 0) {
                char[] ch = initcap0(arr[i].toLowerCase()).toCharArray();
                if (ch[0] >= 'A' && ch[0] <= 'Z') {
                    ch[0] = (char) (ch[0] + 32);
                }
                result += new String(ch);
            } else {
                result += initcap0(arr[i].toLowerCase());
            }
        }
        return result;
    }

    private static String initcap1(String str){
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    private static String initcap0(String str){
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    private static String subStrByPrefix(String str, String preFix){
        return str.indexOf(preFix) == 0 ? str.substring(preFix.length()) : str;
    }
}
