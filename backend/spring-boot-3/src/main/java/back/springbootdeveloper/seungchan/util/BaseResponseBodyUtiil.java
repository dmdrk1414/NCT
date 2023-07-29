package back.springbootdeveloper.seungchan.util;

import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseResponseBodyUtiil {

    public static ResponseEntity<BaseResponseBody> BaseResponseBodySuccess() {
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 200), HttpStatus.OK);
    }
}
