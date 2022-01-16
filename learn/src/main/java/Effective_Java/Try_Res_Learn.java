package Effective_Java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Java7引入
// try-with-resource的优点在于，对于多个资源，可以一起管理
// 同时，在try和finally的异常也可以一起管理
public class Try_Res_Learn {

    static String firstLineOffFile(String path, String defaultVal) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            return br.readLine();
        } catch (Exception e){
            return defaultVal;
        }
    }
}
