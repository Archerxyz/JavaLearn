package LeetCode;

public class Solution_divisor {

    // dividend每次减去2^n个divisor（尽可能多），同时reslut每次加2^n
    public static int divide(int dividend, int divisor) {

        if (dividend == Integer.MIN_VALUE && divisor == -1)
            return Integer.MAX_VALUE;

        boolean mark = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0);

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        int result = 0;

        while (dividend <= divisor) {
            int temp = divisor;
            int c = 1;
            while (dividend - temp <= temp) {
                temp = temp << 1;
                c = c << 1;
            }
            dividend -= temp;
            result += c;
        }
        return mark ? result : -result;
    }

    public static void main(String[] args) {
        System.out.println(divide(-2147483648, -1));
    }
}

