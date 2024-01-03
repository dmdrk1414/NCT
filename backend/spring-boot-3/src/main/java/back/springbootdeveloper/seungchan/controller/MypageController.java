package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.config.jwt.TokenProvider;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.dto.request.UpdateUserFormRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.MyPageResponse;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "내 정보 (mypage) API ", description = "mypage의 정보에 관한 CRUD을 담당한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/mypage")
public class MypageController {
    private final UserService userServiceImp;
    // TODO: 8/7/23 삭제
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @Operation(summary = "내 정보를 조회 한다.", description = "토크를 이용해 사용자를 식별한후 현제 user테이블에 있는 정보들을 조회할 수 있다.")
    @GetMapping("")
    public ResponseEntity<MyPageResponse> findMypage(HttpServletRequest request) {
        // TODO: 토큰을 이용해 유저의 id 찾기
        Long id = tokenService.getUserIdFromToken(request);
        UserInfo user = userServiceImp.findUserById(id);
        return ResponseEntity.ok().body(new MyPageResponse(user));
    }

    @Operation(summary = "내 정보을 업데이트 한다.", description = "토큰을 이용해 정보를 조회한후 user 테이블을 update한다.")
    @PutMapping("/update")
    public ResponseEntity<BaseResponseBody> updateMypage(@RequestBody UpdateUserFormRequest updateUserFormRequest, HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        userServiceImp.updateUser(updateUserFormRequest.toEntity(), userId);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }

    @Operation(summary = "내 정보을 찾는다.", description = "토큰을 이용해 정보를 조회한후 user 테이블을 find한다.")
    @GetMapping("/update")
    public ResponseEntity<MyPageResponse> findMypageToUpdate(HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        UserInfo userInfo = userServiceImp.findUserById(userId);

        return ResponseEntity.ok().body(new MyPageResponse(userInfo));
    }
}
