package back.springbootdeveloper.seungchan.util;

import java.util.List;

public class Utill {
    public static boolean isEqualStr(String arg1, String arg2) {
        return arg1.equals(arg2);
    }

    public static List<String> arrFromStr(String str) {
        return List.of(str.split(", "));
    }
}
