package singleton;

//
public class Singleton_2 {

    private Singleton_2() {
    }

    public static Singleton_2 getInstance() {
        return SingletonHoler.singleton;
    }

    //定义静态内部类
    private static class SingletonHoler {
        //当内部类第一次访问时，创建对象实例
        private static Singleton_2 singleton = new Singleton_2();
    }

}