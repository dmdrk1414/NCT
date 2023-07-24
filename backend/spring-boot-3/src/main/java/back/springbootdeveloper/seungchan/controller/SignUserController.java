package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.dto.request.RequestUserForm;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class SignUserController {
    private final UserService userService;

    @PostMapping("/sign")
    public ResponseEntity<BaseResponseBody> userSignFrom(@RequestBody RequestUserForm requestUserForm) {
        User user = userService.save(requestUserForm);
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 200), HttpStatus.OK);
    }
}
