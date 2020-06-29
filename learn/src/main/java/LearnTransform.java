import java.util.ArrayList;
import java.util.List;

public class LearnTransform {


    public static void main(String[] args) {

        List<String> B = (List<String>)F();

        List<String> C = F();

        List<String> D = new ArrayList<>(F());

        List<Long> E = (List<Long>)G();

        System.out.println(B);
        System.out.println(C);
        System.out.println(D);

        System.out.println(E);
    }

    public static List F(){

        List<Long> A = new ArrayList<>();
        A.add(81043452862799872L);
//        A.add(12345678909787L);

        return A;
    }

    public static List G(){

        List<String> A = new ArrayList<>();
        A.add("81043452862799872");
//        A.add(12345678909787L);

        return A;
    }
}
