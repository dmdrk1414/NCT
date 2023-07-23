package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseBody {
    String message;
    Integer statusCode;
}