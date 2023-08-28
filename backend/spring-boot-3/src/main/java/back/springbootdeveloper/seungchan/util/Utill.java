package back.springbootdeveloper.seungchan.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utill {
    public static boolean isEqualStr(String arg1, String arg2) {
        return arg1.equals(arg2);
    }

    public static List<String> arrFromStr(String str) {
        return List.of(str.split(", "));
    }

    /**
     * @param input "[1,1,1,1]" 의 문자열을 input
     * @return input의 문자열을 숫자만 추출하여 리스트로 만든다.
     */
    public static List<Integer> extractNumbers(String input) {
        List<Integer> integerList = new ArrayList<>();

        // 정규 표현식을 사용하여 숫자 추출
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            // 추출된 문자열을 Integer로 변환하여 리스트에 추가
            integerList.add(Integer.parseInt(matcher.group()));
        }

        return integerList;
    }
}
