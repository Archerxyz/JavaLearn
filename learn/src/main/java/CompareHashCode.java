import glodon.RegionUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompareHashCode {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String DB_URL = "jdbc:mysql://10.129.56.152:3306/gld_gccp_archivedata?useUnicode=true&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASS = "1q2w3e";

    static final String DB_URL_2 = "jdbc:mysql://10.129.248.22:3306/bigdata_search_test?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    static final String USER_2 = "root";
    static final String PASS_2 = "123456";
    // GD0100
//    static final String GLDUSERID = "6016508979726737829";
    // 我
    static final String GLDUSERID = "6241893364905279887";
    public static void main(String[] args) throws IOException, Exception {

        Connection oConnect221 = null;
        Statement oStmt221 = null;
        List<ResultPO> resultPOList = new ArrayList<>();
        int sum = 0;

        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            oConnect221 = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            oStmt221 = oConnect221.createStatement();
            String sql;
            sql = "SELECT " +
                    "bqestidatadetail.bqestidatadetailid, " +
                    "bqestidatadetail.bqspecid, " +
                    "bqestidatadetail.hashcode, " +
                    "bqbaseinfo.region, " +
                    "bqbaseinfo.code, " +
                    "bqspec.spec, " +
                    "bqspec.description" +
                    " FROM " +
                    "bqestidatadetail, " +
                    "bqspec, " +
                    "bqbaseinfo " +
                    " WHERE " +
                    "bqestidatadetail.glduserid = " + GLDUSERID +
                    " AND " +
                    "bqestidatadetail.bqspecid = bqspec.bqspecid " +
                    " AND " +
                    "bqspec.bqbaseinfoid = bqbaseinfo.bqbaseinfoid";
            ResultSet resultSet = oStmt221.executeQuery(sql);

            // 展开结果集数据库
            while (resultSet.next()) {
                // 通过字段检索
                String id = resultSet.getString("bqestidatadetailid");
                String hashcode = resultSet.getString("hashcode");
                String region = RegionUtil.english(resultSet.getString("region")).toUpperCase();
                String code = resultSet.getString("code");


                ResultPO temp = new ResultPO();
                temp.setId(id);
                temp.setHashCode(hashcode);
                temp.setRegion(region);
                temp.setCode(code);

                resultPOList.add(temp);
            }
            // 完成后关闭221
            resultSet.close();
            oStmt221.close();
            oConnect221.close();
            sum = resultPOList.size();


        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (oStmt221 != null) oStmt221.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (oConnect221 != null) oConnect221.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        Connection oConnectBigData = null;
        Statement oStmtBigData = null;

        ResultSet resultSet2 = null;

        try {
            System.out.println("连接数据库...");
            oConnectBigData = DriverManager.getConnection(DB_URL_2, USER_2, PASS_2);
            System.out.println(" 实例化Statement对象...");
            oStmtBigData = oConnectBigData.createStatement();


            int UnMatchCount = 0;

            for (ResultPO resultPO : resultPOList) {
                String sql2;
                sql2 = "SELECT " +
                        "HASHCODE, " +
                        "BQESTIDATADETAILID " +
                        "FROM " +
                        "GLD_BQ_SEARCH_ESTI_DATA_DETAIL_" + resultPO.getRegion() +
                        " WHERE " +
                        "GLD_USER_ID = " + GLDUSERID +
                        " AND " +
                        "HASHCODE = " + resultPO.getHashCode();

                resultSet2 = oStmtBigData.executeQuery(sql2);

                boolean bMatch = false;

                // 展开结果集数据库
                while (resultSet2.next()) {
                    // 通过字段检索
                    bMatch = true;
                    String id2 = resultSet2.getString("BQESTIDATADETAILID");
                    String hashcode2 = resultSet2.getString("HASHCODE");
                }

                if (!bMatch) {
                    System.out.print("hashcode不匹配！ 存档的HashCode为：" + resultPO.getHashCode() + "; 地区为："
                            + resultPO.getRegion() + "; Code为：" + resultPO.getCode() + "\n");
                    UnMatchCount++;
                }
            }

            // 地区汇总：总条数、未匹配条数

            System.out.print("总计 " + sum + " 条存档数据；其中" + UnMatchCount + " 条数据不匹配！" + "\n");

            // 完成后关闭大数据
            resultSet2.close();
            oStmtBigData.close();
            oConnectBigData.close();

        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (oStmtBigData != null) oStmtBigData.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (oConnectBigData != null) oConnectBigData.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println("Goodbye!");
    }
}
