package LeetCode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringTest {

    static ArrayList ans = new ArrayList();
    static int[] num = new int[4];
    static  int[] test = {12,2,3,4,5,6,7,8,8};

    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int res = 0;//记录最长子串的长度
        int end = 0, start = 0;//记录开始和结尾的下标
        Set<Character> set = new HashSet<>();//使用set容器不重复

        String[] tmp = s.split("\\.");
        int temp = Integer.valueOf(s);
        StringBuffer tempBuffer = new StringBuffer(s);
        tempBuffer.reverse();


        Integer.parseInt(s);
        char ch = 'a';
        Character.isLetterOrDigit(ch);
        StringBuffer buffer = new StringBuffer();

        List<Integer> tempList = new ArrayList<>();
        tempList.add(6);
        int a = tempList.stream().reduce(Integer::sum).orElse(0);

        List<String> wordDict = new ArrayList<>();
        new HashSet<>(wordDict);

        // while 来控制一步一步滑动，左右皆是如此
        while (start < n && end < n) {
            if (set.contains(s.charAt(end))) {//如果窗口右侧的字符已经存在
                set.remove(s.charAt(start++));//左侧窗口边界向右
            } else {
                set.add(s.charAt(end++));//如果窗口中无重复，窗口右侧向右滑动
                res = Math.max(res, end - start);//同时记录当前最大长度
            }
        }
        return res;
    }

    public String longestCommonPrefix(String[] strs) {
        String prefix = "";
        if (strs.length != 0) {
            prefix = strs[0];
        }
        int a = Integer.MIN_VALUE;;

        // 想办法取最大公约数
        for (String temp : strs) {
            while (!temp.startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
        prefix.toCharArray();

        StringBuffer temp = new StringBuffer(prefix);
        temp.length();
        temp.reverse().toString();


        return prefix;
    }

    // 字符串排列 滑动窗口2
    public static boolean checkInclusion(String s1, String s2) {

        int WindowsSize = s1.length();
        int begin = 0;
        int end = WindowsSize;
        char[] ar = s1.toCharArray();
        Arrays.sort(ar);
        String sortedS1 = String.valueOf(ar);

        for (;end <= s2.length(); begin++, end++)
        {
            char[] tempAr = s2.substring(begin, end).toCharArray();
            Arrays.sort(tempAr);
            String sortedS2 = String.valueOf(tempAr);
            if (sortedS2.equals(sortedS1)){
                return true;
            }
        }
        return false;
    }


//    public String multiply(String num1, String num2) {
//
//    }


    public static String reverseWords(String s) {
        List<String> wordList = Arrays.stream(s.split(" ")).filter(x -> !x.isEmpty()).collect(Collectors.toList());

        Collections.reverse(wordList);

        return wordList.stream().collect(Collectors.joining(" "));
    }

    public static String simplifyPath(String path) {
        List<String> wordList = Arrays.stream(path.split("/")).filter(x -> !x.isEmpty()).collect(Collectors.toList());

        ArrayDeque<String> queue = new ArrayDeque<>();

        for (String temp : wordList) {
            if (temp.equals("..")) {
                if (queue.size() > 0) {
                    queue.pollLast();
                }
            } else if (temp.equals(".")) {
                // do nothing
            } else {
                queue.addLast(temp);
            }
        }

        return "/" + queue.stream().collect(Collectors.joining("/"));
    }


    public static void dfs(String s, int segId, int segStart) {
        // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
        if (segId == 4) {
            if (segStart == s.length()) {
                String temp = Arrays.asList(num).stream().map(String::valueOf).collect(Collectors.toList()).
                        stream().collect(Collectors.joining("."));

                ans.add(temp);
            }
            return;
        }

        if (segStart == s.length()) {
            return;
        }

        if (s.charAt(segStart) == '0') {
            num[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
        }

        int addr = 0;

        for (int segEnd = segStart; segEnd < s.length(); ++segEnd) {
            addr = addr * 10 + (s.charAt(segEnd) - '0');
            if (addr > 0 && addr <= 0xFF) {
                num[segId]= addr;
                dfs(s, segId + 1, segEnd + 1);
            } else {
                break;
            }
        }

    }


    public static void main (String[]args){
//        System.out.println(checkInclusion("adc","dcda"));
//        System.out.println(reverseWords("  Bob    Loves  Alice   "));
//        System.out.println(simplifyPath("/home//foo/"));

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(8);
        set.add(0);

        List<Integer> temp = new ArrayList<>(set);
        temp = temp.stream().sorted().collect(Collectors.toList());

        dfs("25525511135",0,0);
        System.out.println(ans);
    }

    public String reverse(String str) {
        // 匹配非字母的字符进行分割
        String[] words = str.split("[^A-Za-z]");
        StringBuilder result = new StringBuilder();

        // 逆序添加分割完的单词
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]).append(" ");
        }
        return result.toString().trim();
    }

//    public String largestNumber(int[] nums) {
//    //数组求和：
//        int sum = IntStream.of(nums).sum();
//
//
//    }
}
