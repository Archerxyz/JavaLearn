package Effective_Java;

public class Enum_Learn {
    // 大型枚举类实现1
    public enum Plant {

        // 注意，枚举类型的值一定要写在这些构造函数前面，不然无法编译
        MERCURY(3.302e+23,2.439e6);

        private final double mass;
        private final double redius;
        private final double surfaceGravity;
        private static final double G = 6.67300E-11;

        Plant(double mass, double redius){
            this.mass = mass;
            this.redius = redius;
            this.surfaceGravity = G * mass / (redius * redius);
        }

        public double getMass(){
            return mass;
        }

        public double getRedius() {
            return redius;
        }

        public double getSurfaceGravity() {
            return surfaceGravity;
        }
    }


    // 操作型枚举类实现
    // 实际上是Override
    public enum Operation{
        PLUS{
            @Override
            public double appply(double x, double y) {
                return x + y;
            }
        },

        TIMES{
            @Override
            public double appply(double x, double y) {
                return x * y;
            }
        },

        DIVIDE{
            @Override
            public double appply(double x, double y) {
                return x / y;
            }
        },

        MINUS{
            @Override
            public double appply(double x, double y) {
                return x - y;
            }
        };


        public abstract double appply(double x, double y);
    }



    public static void main(String args[]){
        double mass = Plant.MERCURY.getMass();

        // 遍历枚举集合
        for(Plant p :Plant.values()){
            System.out.println("");
        }
    }


}
