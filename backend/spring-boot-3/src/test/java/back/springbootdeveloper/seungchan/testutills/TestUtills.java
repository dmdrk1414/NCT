package back.springbootdeveloper.seungchan.testutills;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestUtills {
    private static final String HTTP_STATUS = "httpStatus";
    private static final String HTTP_STATUS_CODE = "stateCode";
    private static final String MESSAGE = "message";

    /**
     * 실패를 한 response에서 HttpStatus을 얻는다.
     *
     * @param response
     * @return
     */
    public static HttpStatus getHttpStatusFromResponse(MockHttpServletResponse response) {
        Map<String, String> responseMap = responseToMap(response);

        return HttpStatus.valueOf(responseMap.get(HTTP_STATUS));
    }

    /**
     * Custom Https Status을 사용할시
     * 실패를 한 response에서 HttpStatus Code을 얻는다.
     *
     * @param response
     * @return
     */
    public static Integer getCustomHttpStatusCodeFromResponse(MockHttpServletResponse response) {
        Map<String, String> responseMap = responseToMap(response);

        return Integer.valueOf(responseMap.get(HTTP_STATUS_CODE));
    }

    /**
     * 실패를 한 response에서 message을 얻는다.
     *
     * @param response
     * @return
     */
    public static String getMessageFromResponse(MockHttpServletResponse response) {
        Map<String, String> responseMap = responseToMap(response);

        return responseMap.get(MESSAGE);
    }

    /**
     * BCryptPasswordEncoder을 한 password와 rawPassword을 비교한다.
     *
     * @param rawPassword     Encoder하기전 PW
     * @param encodedPassword Encoder한후 PW
     * @return
     */
    public static Boolean checkPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    /**
     * response을 Map으로 변환
     *
     * @param response
     * @return
     */
    private static Map<String, String> responseToMap(MockHttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(response.getContentAsString(), new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
