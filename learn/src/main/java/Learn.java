import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static java.util.ArrayList.*;
import static oracle.jrockit.jfr.events.Bits.intValue;

public class Learn {

    // 内部类？
    class LearnException extends FileNotFoundException {
        public LearnException() {
        }

        public LearnException(String message) {
            super(message);
        }
    }


    public static void main(String[] args) {
        A();
        try{
            System.out.println(B(1, 2, 3, 4, 5, 6, 76, 87));
        }
        catch (LearnException e)
        {

        }
        C();
    }

    private static void A() {
        String d = "ok";
        System.out.println(d.hashCode());
        System.out.println(d);
        Integer a = 8;
        int aTemp = intValue(a);

        // 在这处理异常，是自己处理
        try {
            InputStream in = new FileInputStream("a.txt");
        } catch (FileNotFoundException le) {
            // 打印栈轨迹
            le.printStackTrace();
        } finally {
            // 无论异常是否捕获，finally中的代码都将会被执行
            // 常用于关闭资源！
        }
        System.out.println(aTemp);
    }

    // 声明中加throws，交由调用者去处理
    private static int B(int... values) throws LearnException {
        int lagest = 0;
        for (int v : values)
            if (v > lagest)
                lagest = v;
        return lagest;
    }

    private static void C() {
        LearnExtend[] TempObjectList = new LearnExtend[3];

        TempObjectList[0] = new LearnExtend(1, 0.1);
        TempObjectList[1] = new LearnExtend(2, 4.4);
        TempObjectList[2] = new LearnExtend(3, 7.5);

        System.out.println(TempObjectList.getClass().toString());

        Arrays.sort(TempObjectList);
    }
}


