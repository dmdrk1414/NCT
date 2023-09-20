package back.springbootdeveloper.seungchan.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    /**
     * @param target_1 비교대상_1
     * @param target_2 비교대상_2
     * @return 비교대상이 같으면 true, 다르면 false
     */
    public static boolean isSameInteger(int target_1, int target_2) {
        if (Integer.compare(target_1, target_2) == 0) {
            return true;
        }
        return false;
    }

    /**
     * printf ObjectList
     * 출력
     * 객체의 정보의 한줄
     * 객체의 정보의 한줄
     */
    public static <T> void printObjectList(List<T> objectList) {
        System.out.println("printObjectList");
        for (int index = 0; index < objectList.size(); index++) {
            System.out.println(index + ": " + objectList.get(index));
        }
        System.out.println();
    }
}
