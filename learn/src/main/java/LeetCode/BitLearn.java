package LeetCode;

public class BitLearn {



    private int[] partition2(int[] nums) {
        int temp = 1723;
        int temp2 = 1;

        temp  = temp << 1;


        while((temp2 & temp) == 0){
            temp2 = temp2 << 1;
        }

        return  new int[]{1,2};

    }
}
