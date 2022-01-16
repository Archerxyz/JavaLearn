package singleton;

import java.util.ArrayList;
import java.util.List;

// 双重检查锁 单例！
public class DoubleCheckSingleton {

    // 关键1 加volatile 这是由于new的时候 可能发生重排序，导致出现问题！
    private static volatile DoubleCheckSingleton instance = null;

    private DoubleCheckSingleton(){};

    public static DoubleCheckSingleton getInstance() {

        if (instance == null)
        {
            synchronized (DoubleCheckSingleton.class)
            {
                // 关键2 保证时刻单例
                if (instance == null)
                {
                    instance = new DoubleCheckSingleton();
                }
            }
        }

        return instance;
    }
}
