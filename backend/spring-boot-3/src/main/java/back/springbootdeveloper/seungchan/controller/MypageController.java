package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.dto.response.MyPageResponse;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import back.springbootdeveloper.seungchan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class MypageController {
    private final UserService userService;

    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponse> findMypage() {
        // TODO: 토큰을 이용해 유저의 id 찾기
        Long id = 1L; // 박승찬
        User user = userService.findUserById(id);
        return ResponseEntity.ok().body(new MyPageResponse(user));
    }
}
