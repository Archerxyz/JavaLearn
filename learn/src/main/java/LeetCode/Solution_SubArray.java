package LeetCode;

import java.util.Arrays;

public class Solution_SubArray {

    private static int[] test = {3, 1, 2, 4};

    public static int sumSubarrayMins(int[] A) {

        Arrays.sort(A);
        int size = A.length;
        int sum = 0;

        for (int i = 0; i < A.length; i++) {
            sum += (A[i] * size);
            size--;
        }

        return sum;
    }


    public static void main(String[] args) {

        System.out.println(sumSubarrayMins(test));
    }
}
