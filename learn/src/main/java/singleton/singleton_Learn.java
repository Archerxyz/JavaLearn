package singleton;


// 懒汉模式！
public class singleton_Learn {

    // 类初始化，不初始化这个对象（即不写 = new EagerSingleton();） 否则就是饥饿模式
    private static singleton_Learn instance = null;

    // 构造函数私有化，不给调！
    private singleton_Learn(){};

    // 方法同步，保证线程安全，但是性能低。
    public static synchronized singleton_Learn getInstance(){
        if (instance == null)
        {
            instance = new singleton_Learn();
        }
        return instance;
    }
}
