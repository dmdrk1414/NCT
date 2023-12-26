package back.springbootdeveloper.seungchan.testutills;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestUtills {
    private static final String HTTP_STATUS = "httpStatus";
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