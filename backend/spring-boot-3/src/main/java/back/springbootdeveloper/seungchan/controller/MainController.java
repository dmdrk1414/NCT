package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.ObUser;
import back.springbootdeveloper.seungchan.domain.UserInfo;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.dto.response.ObUserOfMainResponse;
import back.springbootdeveloper.seungchan.dto.response.UserOfDetail2MainResponse;
import back.springbootdeveloper.seungchan.domain.YbUserInfomation;
import back.springbootdeveloper.seungchan.dto.response.YbUserListResponse;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import back.springbootdeveloper.seungchan.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Tag(name = "main page API", description = "로그인을 한후의 main page이다. yb, ob의 정보을 얻을 수 있다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/main")
public class MainController {
    private final UserService userServiceImp;
    private final UserUtilRepository userUtilRepository;
    private final UserOfMainService userOfMainService;
    private final UserUtillService userUtillService;
    private final TokenService tokenService;
    private final AttendanceService attendanceService;

    @Operation(summary = "main page 현재 인원들의 정보", description = "main page 현재 재학 인원들의 정보들을 나열")
    @GetMapping("/ybs")
    public ResponseEntity<YbUserListResponse> findAllYbUser(HttpServletRequest request) {
        Long UserIdOfSearch = tokenService.getUserIdFromToken(request);
        boolean isPassAttendanceOfSearchUser = attendanceService.isPassAttendanceAtToday(UserIdOfSearch);
        boolean isObUser = false;
        List<YbUserInfomation> ybUserInfomationList = userOfMainService.findAllByIsOb(isObUser);
        return ResponseEntity.ok().body(YbUserListResponse.builder()
                .ybUserInfomationList(ybUserInfomationList)
                .isPassAttendanceOfSearchUse(isPassAttendanceOfSearchUser)
                .build());
    }

    @Operation(summary = "main page 졸업 인원들의 정보", description = "main page 졸업 인원들의 정보들을 나열, 실장들과 일반인들이 볼수 있는 정보가 나누어져 있다.")
    @GetMapping("/obs")
    public ResponseEntity<List<ObUserOfMainResponse>> findAllObUser(HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        boolean isNuriKing = tokenService.getNuriKingFromToken(request);
        List<ObUser> obUserList = userOfMainService.findAllObUser();

        return ResponseEntity.ok().body(Collections.singletonList(new ObUserOfMainResponse(obUserList, isNuriKing)));
    }

    @Operation(summary = "main page의 회원들의 정보를 자세하게 조회", description = "main page의 회원들의 정보를 실장과 일반 회원들의 권한 별로 볼수 있는 정보가 다르다.")
    @GetMapping("/detail/{id}")
    public ResponseEntity<UserOfDetail2MainResponse> fetchUserOfDetail2Main(HttpServletRequest request, @PathVariable long id) {
        Long userIdOfSearch = tokenService.getUserIdFromToken(request);
        UserInfo user = userServiceImp.findUserById(id);

        Long userId = user.getId();
        UserUtill userUtill = userUtilRepository.findByUserId(userIdOfSearch);

        UserOfDetail2MainResponse response = new UserOfDetail2MainResponse(userUtill, user);

        return ResponseEntity.ok().body(response);
    }

}
