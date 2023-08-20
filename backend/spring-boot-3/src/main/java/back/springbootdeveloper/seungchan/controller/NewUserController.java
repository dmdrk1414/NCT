package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.TempUser;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.dto.request.NewUserApprovalRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.NewUserEachResponse;
import back.springbootdeveloper.seungchan.dto.response.NewUsersResponse;
import back.springbootdeveloper.seungchan.service.TempUserService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "신청 유저들의 정보 관련 API", description = "신청 유저들의 정보를 당담한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/new-users")
public class NewUserController {
    private final TempUserService tempUserService;
    private final UserService userService;
    private final TokenService tokenService;

    @Operation(summary = "모든 신청 유저들의 정보 보기", description = "3명이 신청을 하면 3명의 정보를 모두 확인가능하다.")
    @GetMapping("")
    public ResponseEntity<List<NewUsersResponse>> findAllNewUsers() {
        List<NewUsersResponse> newUserList = tempUserService.findAllNewUsers();
        return ResponseEntity.ok().body(newUserList);
    }

    @Operation(summary = "신청 개별 유저들의 정보 보기", description = "신청을 한 유저의 정보를 확인가능하다.")
    @GetMapping("/{id}")
    public ResponseEntity<NewUserEachResponse> findNewUsers(@PathVariable long id, HttpServletRequest request) {
        TempUser tempUser = tempUserService.findNewUsers(id);
        boolean isNuriKingOfToken = tokenService.getNuriKingFromToken(request);
        return ResponseEntity.ok().body(NewUserEachResponse.builder()
                .tempUser(tempUser)
                .isNuriKing(isNuriKingOfToken)
                .build());
    }

    @Operation(summary = "실장의 추가 실원 승락 API", description = "실장이 신청 인원의 개별 페이지에서 승락 버튼구현")
    @PostMapping("/{id}/acceptance")
    public ResponseEntity<BaseResponseBody> acceptNewUserOfKing(@RequestBody NewUserApprovalRequest newUserApprovalRequest, HttpServletRequest request) {
        boolean isNuriKing = tokenService.getNuriKingFromToken(request);
        Long idOfNewUser = newUserApprovalRequest.getId();
        if (isNuriKing) {
            TempUser tempUser = tempUserService.removeTempUserById(idOfNewUser);
            User newUser = User.getUserFromTempUser(tempUser);
            userService.saveNewUser(newUser);
        }
        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }

    @Operation(summary = "실장의 추가 실원 거절 API", description = "실장이 신청 인원의 개별 페이지에서 거절 버튼구현")
    @PostMapping("/{id}/reject")
    public ResponseEntity<BaseResponseBody> rejectNewUserOfKing(@RequestBody NewUserApprovalRequest newUserApprovalRequest, HttpServletRequest request) {
        boolean isNuriKing = tokenService.getNuriKingFromToken(request);
        Long idOfNewUser = newUserApprovalRequest.getId();
        if (isNuriKing) {
            tempUserService.removeTempUserById(idOfNewUser);
        }
        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }
}