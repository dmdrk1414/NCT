package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.NewUsersResponse;
import back.springbootdeveloper.seungchan.service.TempUserService;
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

    @Operation(summary = "모든 신청 유저들의 정보 보기", description = "3명이 신청을 하면 3명의 정보를 모두 확인가능하다.")
    @GetMapping("")
    public ResponseEntity<List<NewUsersResponse>> findAllNewUsers(HttpServletRequest request) {
        List<NewUsersResponse> newUserList = tempUserService.findAllNewUsers();
        return ResponseEntity.ok().body(newUserList);
    }
}