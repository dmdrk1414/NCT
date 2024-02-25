package back.springbootdeveloper.seungchan.controller;


import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.service.ClubService;
import back.springbootdeveloper.seungchan.service.EntityDeleteService;
import back.springbootdeveloper.seungchan.service.MemberService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "마이페이지", description = "마이페이지 관련 API")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/personal-info/{club_member_id}")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;
    private final EntityDeleteService entityDeleteService;
    private final ClubService clubService;
    private final TokenService tokenService;

    @Operation(summary = "마이페이지 - 동아리 탈퇴하기", description = "클럽 인원이 클럽을 탈퇴한다.")
    @PostMapping(value = "/quit")
    public ResponseEntity<BaseResponseBody> quitClubMember(
            @PathVariable(value = "club_member_id") Long clubMemberId,
            HttpServletRequest request) {
        // 클럽 이름
        String clubName = clubService.getClubName(clubMemberId);
        // 멤버 추방
        entityDeleteService.expulsionMemberFromClub(clubMemberId);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess(RESPONSE_MESSAGE_VALUE.SUCCESS_QUIT_CLUB(clubName));
    }
}
