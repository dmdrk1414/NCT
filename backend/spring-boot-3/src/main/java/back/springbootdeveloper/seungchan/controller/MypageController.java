package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.config.jwt.TokenProvider;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.dto.request.RequestUserForm;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.MyPageResponse;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class MypageController {
    private final UserService userServiceImp;
    // TODO: 8/7/23 삭제
    private final TokenProvider tokenProvider;

    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponse> findMypage() {
        // TODO: 토큰을 이용해 유저의 id 찾기
        Long id = 1L; // 박승찬
        User user = userServiceImp.findUserById(id);
        return ResponseEntity.ok().body(new MyPageResponse(user));
    }

    @PutMapping("/mypage/update")
    public ResponseEntity<BaseResponseBody> updateMypage(@RequestBody RequestUserForm requestUserForm) {
        Long userId = 1L;
        userServiceImp.updateUser(requestUserForm.toEntity(), userId);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }
}
