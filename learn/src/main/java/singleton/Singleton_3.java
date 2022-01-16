package singleton;

public class Singleton_3 {

    private static Singleton_3 singleton3 = null;

    private Singleton_3() {
    }

    public static Singleton_3 getInstance() {
        // 为了提高效率，仅当未实例化的时候才调用同步锁
        if (singleton3 == null) {
            synchronized (Singleton_3.class) {
                // 在并发状态中，再检查一次
                if (singleton3 == null) {
                    singleton3 = new Singleton_3();
                }
            }
        }
        return singleton3;
    }
}
