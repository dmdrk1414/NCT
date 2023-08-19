package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.TempUser;
import back.springbootdeveloper.seungchan.dto.response.NewUserEachResponse;
import back.springbootdeveloper.seungchan.dto.response.NewUsersResponse;
import back.springbootdeveloper.seungchan.service.TempUserService;
import back.springbootdeveloper.seungchan.service.TokenService;
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
}