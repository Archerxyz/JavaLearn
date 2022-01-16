package LeetCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LeetCode202 {
    public static boolean isValid(String s) {
        // write code here
        LinkedList<Character> link = new LinkedList();

        for(int i = 0; i < s.length(); i++)
        {
            char temp = s.charAt(i);
            if (temp == ')' || temp == ']' || temp == '}')
            {

                System.out.println(link.getFirst() + 1);
                System.out.println(temp + 0);
                if (link.isEmpty() || temp + 0 != link.getFirst() + 1)
                    return false;

                link.removeFirst();
            }
            else{
                link.addFirst(s.charAt(i));
            }
        }
        return link.size() == 0;
    }

    public static void main(String[] args) {

        String s = "12314123";
        s.replaceAll(" ", "%20");
        System.out.println(isValid("()"));
    }
}
