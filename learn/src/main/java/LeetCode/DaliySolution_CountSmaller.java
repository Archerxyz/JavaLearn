package LeetCode;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class DaliySolution_CountSmaller {
    public static List<Integer> countSmaller(int[] nums) {

        int[] preResult = new int[1000];
        int[] repeatNum = new int[1000];
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = nums.length - 1; i >= 0; i--) {
            repeatNum[nums[i]]++;
            for (int k = nums[i] - 1; k >= 0; k--) {
                if (k == 0 && preResult[0] == 0) {
                    list.push(0);
                    preResult[nums[i]]++;
                    break;
                } else if (preResult[k] != 0) {
                    list.push(preResult[k]);
                    preResult[nums[i]] = preResult[k] + repeatNum[nums[i]];
                    break;
                }
            }
        }

        return list;
    }


    public static void main(String[] args) {
        int num[] = {9, 3, 2, 1, 4, 9, 6, 5, 2, 6, 1};
        System.out.println(countSmaller(num));
    }
}
