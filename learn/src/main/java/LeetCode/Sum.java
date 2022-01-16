package LeetCode;

import javafx.util.Pair;

import java.util.*;

public class Sum {
    public int[] twoSum(int[] nums, int target) {

        int[] result = new int[2];

        for (int i = 0; i < nums.length; i++) {
            int num1 = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int num2 = nums[j];

                if (num1 + num2 == target) {
                    result[0] = num1;
                    result[1] = num2;
                    return result;
                }
            }
        }

        return result;
    }

    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(target - nums[i])) {
                return new int[]{i, hashMap.get(target - nums[i])};
            }
            hashMap.put(nums[i], i);
        }
        return new int[0];
    }



    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            x /= 10;
            rev = rev * 10 + digit;
        }
        return rev;
    }

    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;

        if (String.valueOf(x).endsWith("0"))
            return false;

        String result = new StringBuilder(String.valueOf(x)).reverse().toString();

        return result.equals(String.valueOf(x));
    }

    public boolean isPalindrome2(int x) {
        // 特殊情况：
        // 如上所述，当 x < 0 时，x 不是回文数。
        // 同样地，如果数字的最后一位是 0，为了使该数字为回文，
        // 则其第一位数字也应该是 0
        // 只有 0 满足这一属性
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
        // 例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，revertedNumber = 123，
        // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
        return x == revertedNumber || x == revertedNumber / 10;
    }

    public boolean isValid(String s) {
            int n = s.length();
            if (n % 2 == 1) {
                return false;
            }

            Map<Character, Character> pairs = new HashMap<Character, Character>() {{
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }};
            Deque<Character> stack = new LinkedList<Character>();
            for (int i = 0; i < n; i++) {
                char ch = s.charAt(i);
                if (pairs.containsKey(ch)) {
                    if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                        return false;
                    }
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }
            return stack.isEmpty();
        }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            ListNode result = new ListNode(0);
            ListNode l3 = result;

            while (l1 != null && l2 != null) {
                if (l1.val <= l2.val) {
                    l3 = l1;
                    l1 = l1.next;
                } else {
                    l3 = l2;
                    l2 = l2.next;
                }
                l3 = l3.next;
            }

            l3.next = l1 == null ? l2 : l1;

            return result.next;
        }
    }

    public int removeDuplicates(int[] nums) {
        int fast = 1;
        int slow = 1;


        for (fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    public int removeElement(int[] nums, int val) {
        int fast = 0;
        int slow = 0;

        for (fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }

    public int searchInsert(int[] nums, int target) {

        int high = nums.length - 1;
        int low = 0;
        int ans = nums.length;

        if (target > nums[nums.length - 1]) {
            return nums.length;
        }

        if (target < nums[0]) {
            return 0;
        }


        while (low <= high) {
            int half = (high - low) / 2 + low;
            if (nums[half] > target) {
                // 如果数组中没有这个数字的话，位置应该要往前插一位，所以在这里给ans赋值
                ans = half;
                high = half - 1;

            } else if (nums[half] < target) {
                low = half + 1;
            } else if (nums[half] == target) {
                return half;
            }
        }

        return ans;
    }

    // 第53题：动态规划
    // 理解：转移方程 pre代表的不是 上一子集中的最大和，而是带上上一元素后的最大和！ 如果要带上上一元素得考虑收益的正负，若是负的就没有必要
    // 累加前面的队列了。
    // 我们用 f(i)f(i) 代表以第 i 个数结尾的「连续子数组的最大和」，以考量带来此元素的影响。若是正的就可以累加！
        public int maxSubArray(int[] nums) {
            int pre = 0, maxAns = nums[0];
            for (int x : nums) {
                pre = Math.max(pre + x, x);
                maxAns = Math.max(maxAns, pre);
            }
            return maxAns;
    }

    // 所有素数
    public int eratosthene(int n) {
        boolean[] isPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!isPrime[i]) {
                count++;
                // 标记所有素数的倍数，直至n
                // 由于小于平方数的树一定会被遍历过，因此可以这样降低时间复杂度。
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = true;
                }
            }
        }
        return count;
    }

    // 斐波那契数列 -1
    public int fbnq(int n) {

        if (n == 0)
        {
            return 0;
        }
        else if (n == 1)
        {
            return 1;
        }
        else
        {
            return fbnq(n-1) + fbnq(n-2);
        }

    }

    // 斐波那契数列 - 2 去重递归 思路 存储过程值。

//    // 斐波那契数列 - 3 仅保存临时值 迭代非递归
//    public int fbnq2(int n) {
//        int[] sum = new int[n + 1];
//
//    }


    // 合并数组 双指针
    public void merge(int[] nums1, int m, int[] nums2, int n) {

        LinkedHashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>(6,0);
        temp.getOrDefault(1,-1);
    }







}
