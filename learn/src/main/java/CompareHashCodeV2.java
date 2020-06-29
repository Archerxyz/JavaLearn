import glodon.Region;
import glodon.RegionUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompareHashCodeV2 {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String DB_URL_221 = "jdbc:mysql://10.129.56.152:3306/gld_gccp_archivedata?useUnicode=true&characterEncoding=UTF-8";
    static final String USER_221 = "root";
    static final String PASS_221 = "1q2w3e";

    static final String DB_URL_Bigdata_Search = "jdbc:mysql://10.129.248.22:3306/bigdata_search_test?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    static final String USER_Bigdata_Search = "root";
    static final String PASS_Bigdata_Search = "123456";
    // 214850426@QQ.COM
    // static final String GLDUSERID = "6381742990960960061";

    // GD0100
//    static final String GLDUSERID = "6016508979726737829";
    // 新账号
    static final String GLDUSERID = "6241893364905279887";
    // GD0900
//    static final String GLDUSERID = "6016508970637939018";

    public static void main(String[] args) throws IOException, Exception {

        Connection oConnectBigData = null;
        Statement oStmtBigData = null;
        List<ResultPO> resultPOList = new ArrayList<>();
        List<ResultPO> UnMatchList = new ArrayList<>();

        int sum = 0;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库...");
            oConnectBigData = DriverManager.getConnection(DB_URL_Bigdata_Search, USER_Bigdata_Search, PASS_Bigdata_Search);
            System.out.println(" 实例化Statement对象...");
            oStmtBigData = oConnectBigData.createStatement();

            List<Region> regionList = RegionUtil.allRegions();
            ResultSet resultSetFromBigdata = null;

            for (Region region : regionList) {
                if (!region.getChineseName().equals("河南")
//                        || !region.getChineseName().equals("天津")
//                        || !region.getChineseName().equals("上海")
                ) {
                    continue;
                }
                String sql;
                String estiTableName = "GLD_BQ_SEARCH_ESTI_DATA_DETAIL_" + region.getEnglishName().toUpperCase();
                String specTableName = "GLD_BQ_SEARCH_SPEC_" + region.getEnglishName().toUpperCase();

                sql = "SELECT " +
                        "HASHCODE, " +
                        "BQSPECID " +
                        " FROM " +
                        estiTableName +
                        " WHERE " +
                        "GLD_USER_ID = " + GLDUSERID;

                resultSetFromBigdata = oStmtBigData.executeQuery(sql);

                // 展开结果集数据库
                while (resultSetFromBigdata.next()) {
                    // 通过字段检索
                    String hashcode = resultSetFromBigdata.getString("HASHCODE");
                    String regionName = region.getChineseName();
//                    String spec = resultSetFromBigdata.getString("SPEC");
//                    String description = resultSetFromBigdata.getString("DESCRIPTION");
                    String specid = resultSetFromBigdata.getString("BQSPECID");

                    ResultPO temp = new ResultPO();
                    temp.setHashCode(hashcode);
                    temp.setRegion(regionName);
//                    temp.setDescription(description);
//                    temp.setSpec(spec);
                    temp.setSpecid(specid);

                    resultPOList.add(temp);
                }
            }

            resultSetFromBigdata.close();
            oStmtBigData.close();
            oConnectBigData.close();
            sum = resultPOList.size();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (oStmtBigData != null)
                    oStmtBigData.close();
            } catch (SQLException se2) {
                try {
                    if (oConnectBigData != null)
                        oConnectBigData.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }

        Connection oConnect221 = null;
        Statement oStmt221 = null;
        ResultSet resultSetFrom221 = null;

        try {
            System.out.println("连接数据库...");
            oConnect221 = DriverManager.getConnection(DB_URL_221, USER_221, PASS_221);
            System.out.println(" 实例化Statement对象...");
            oStmt221 = oConnect221.createStatement();

            int UnMatchCount = 0;
            for (ResultPO resultPO : resultPOList) {
                String sql2;
                sql2 = "SELECT " +
                        "bqestidatadetail.bqestidatadetailid, " +
                        "bqestidatadetail.bqspecid " +
                        "FROM " +
                        "bqestidatadetail " +
                        "WHERE " +
                        "bqestidatadetail.hashcode = " + resultPO.getHashCode();

                resultSetFrom221 = oStmt221.executeQuery(sql2);
                boolean bMatch = false;

                // 展开结果集数据库
                while (resultSetFrom221.next()) {
                    // 通过字段检索
                    bMatch = true;
                    String id = resultSetFrom221.getString("bqestidatadetailid");
                }

                if (!bMatch) {
                    System.out.print("hashcode不匹配！ HashCode为：" + resultPO.getHashCode() + "; 地区为："
                            + resultPO.getRegion() + "; SPECID为：" + resultPO.getSpecid() +  "\n");
                    UnMatchCount++;
                    UnMatchList.add(resultPO);
                }
            }

            System.out.print("总计 " + sum + " 条工程化数据；其中" + UnMatchCount + " 条数据不匹配！" + "\n");

            // 完成后关闭大数据
            resultSetFrom221.close();
            oStmt221.close();
            oConnect221.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (oStmt221 != null)
                    oStmt221.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (oConnect221 != null)
                    oConnect221.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println("Goodbye!");
    }
}
