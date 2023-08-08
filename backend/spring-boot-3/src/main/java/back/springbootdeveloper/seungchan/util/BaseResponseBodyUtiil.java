package back.springbootdeveloper.seungchan.util;

import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponseBodyUtiil {

    public static ResponseEntity<BaseResponseBody> BaseResponseBodySuccess() {
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 200), HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponseBody> BaseResponseBodyForbidden() {
        return new ResponseEntity<>(new BaseResponseBody("Access denied", 403), HttpStatus.FORBIDDEN);
    }
}
