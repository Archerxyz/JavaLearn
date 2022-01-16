package JDK1_8;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regularExpression {
    public static void main(String[] args) {

        String regular1 = "[A-Za-z].+";

        String input = "hanx-g韩叙村上春树ZXA123123/n123123123123/n123123";

        String input2 = "asdasdawdazsdas";

        String input3 = "没有那个必要";

        List<String> inputList = new ArrayList<>();

        inputList.add(input);
        inputList.add(input2);

        Pattern pattern1 = Pattern.compile(regular1, Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);

        Matcher matcher1 =  pattern1.matcher(input);

//        System.out.println(matcher1.toMatchResult().group());
        System.out.println(matcher1);

    }
}
