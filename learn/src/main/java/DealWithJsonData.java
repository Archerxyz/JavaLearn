import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

public class DealWithJsonData {

    private static final String SOUCRE_FILE_PATH = "G:\\SourceFile";
    private static final String DEST_FILE_PATH = "G:\\DestFile\\";

    public static void main(String[] args) {

        String path = SOUCRE_FILE_PATH; //获取路径
        File oFileDir = new File(path); //新建文件实例
        File[] list = oFileDir.listFiles(); /* 此处获取文件夹下的所有文件 */

        for (int i = 0; i < list.length; i++) {
            JSONObject oJson = getJson(list[i].getAbsolutePath());
            creatJsonFile(oJson, list[i].getName());
        }


    }

    private static JSONObject getJson(String sPath) {

        StringBuffer oModelBuffer = new StringBuffer();

        try {
            ZipFile zf = new ZipFile(sPath);
            Enumeration<? extends ZipEntry> zipEntry = zf.entries();

            ZipEntry ze;

            while (zipEntry.hasMoreElements()) {
                ze = zipEntry.nextElement();
                if (ze.isDirectory()) {

                }
                else if (ze.toString().equals("Model")) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zf.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        oModelBuffer.append(line);
                    }
                    br.close();
                }
            }

//            String input = FileUtils.readFileToString(new File(sPath), "UTF-8");

            oModelBuffer.toString();
            JSONObject oJson = JSON.parseObject(oModelBuffer.toString());
            return oJson;
        } catch (IOException e) {
            return null;
        }
    }

    private static void creatJsonFile(JSONObject oJson, String sFileName) {

        Set<String> oKeySet = oJson.keySet();

        HashMap<String, Integer> oNameMap = new HashMap();

        JSONObject oNewJson = new JSONObject();
        int ID = 0;
        for (String key : oKeySet) {
            if (oJson.getJSONArray(key).size() > 0) {
                JSONArray innerJsonArray = oJson.getJSONArray(key);
                JSONArray newInnerJsonArray = new JSONArray();
                for (int i = 0; i < innerJsonArray.size(); ++i) {
                    JSONObject oInnerJson = innerJsonArray.getJSONObject(i);
                    Set<String> oInnerKeySet = oInnerJson.keySet();
                    JSONObject oNewInnerJson = new JSONObject();
                    for (String innerKey : oInnerKeySet) {
                        if (oNameMap.get(innerKey) == null) {
                            oNameMap.put(innerKey, ID);
                            oNewInnerJson.put(Integer.toString(ID), oInnerJson.get(innerKey).toString());
                            ID++;
                        } else {
                            oNewInnerJson.put(Integer.toString(oNameMap.get(innerKey)), oInnerJson.get(innerKey).toString());
                        }
                    }
                    newInnerJsonArray.add(oNewInnerJson);
                }

                if (oNameMap.get(key) == null) {
                    oNameMap.put(key, ID);
                    oNewJson.put(Integer.toString(ID), newInnerJsonArray);
                    ID++;
                } else {
                    oNewJson.put(Integer.toString(oNameMap.get(key)), newInnerJsonArray);
                }

            }
        }

        System.out.println(oNewJson);

        String sPath = DEST_FILE_PATH + sFileName;

        // 保证创建一个新文件
        try {
            File file = new File(sPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

            OutputStream outputStream = new FileOutputStream(file);
            byte[] reducedFile = zip(oNewJson.toJSONString());



            outputStream.write(reducedFile);
            outputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static byte[] zip(String foo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream gzos = null;
        try {
            gzos = new ZipOutputStream(baos);
            gzos.setLevel(9);
            gzos.putNextEntry(new ZipEntry("Model"));
            gzos.write(foo.getBytes("UTF-8"));
        } catch (IOException e) {
        } finally {
            if (gzos != null) {
                try {
                    gzos.closeEntry();
                } catch (IOException e) {
                }
            }
            try {
                gzos.close();
            } catch (IOException ignore) {
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException ignore) {
                }
            }
        }
        return baos.toByteArray();
    }
}
