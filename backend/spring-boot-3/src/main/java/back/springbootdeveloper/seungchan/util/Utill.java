package back.springbootdeveloper.seungchan.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

public class Utill {
    public static boolean isEqualStr(String arg1, String arg2) {
        return arg1.equals(arg2);
    }

    public static List<String> arrFromStr(String str) {
        return List.of(str.split(", "));
    }

    public static String getToken(HttpServletRequest request) {
        // HTTP Request에서 "Authorization" 헤더 값 얻기
        String header = request.getHeader("Authorization");

        // 토큰이 없는 경우나 "Bearer " 접두사를 포함하지 않은 경우 처리
        if (header == null || !header.startsWith("Bearer ")) {
            throw new BadCredentialsException("Invalid token");
        }

        // "Bearer " 접두사를 제거하여 실제 토큰 얻기
        String token = header.replace("Bearer ", "");

        return token;
    }
}
