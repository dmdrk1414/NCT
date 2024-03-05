package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.service.ClubMemberInformationService;
import back.springbootdeveloper.seungchan.service.EntityApplyService;
import back.springbootdeveloper.seungchan.service.MemberService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "스터디 팀을 등록하는 컨트롤러", description = "팀이름, 팀소개, 팀프로필 사진, 팀 소개사진을 입력받는다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/main/club/apply")
public class ClubRegistrationController {

  private final EntityApplyService entityApplyService;
  private final TokenService tokenService;
  private final MemberService memberService;
  private final ClubMemberInformationService clubMemberInformationService;

  @Operation(summary = "개인 회원 - 팀 등록 api ")
  @PostMapping("")
  public ResponseEntity<BaseResponseBody> getClubRegistraionInfo(
      HttpServletRequest request,
      @RequestParam(value = "clubName") String clubName,
      @RequestParam(value = "clubIntroduction") String clubIntroduction,
      @RequestParam(value = "clubProfileImages", required = false) MultipartFile clubProfileImage,
      @RequestPart(name = "clubInformationImages", required = false) List<MultipartFile> clubInformationImages) {
    Long memberId = tokenService.getMemberIdFromToken(request);
    Member myMember = memberService.findByMemberId(memberId);

    Club saveClub = entityApplyService.makeClub(
        clubName, clubIntroduction, clubProfileImage, clubInformationImages
    ).orElseThrow(EntityNotFoundException::new);

    ClubMemberInformation clubMemberInformation = clubMemberInformationService.makeLeaderClubMemberInformation(
        saveClub, myMember);

    ClubMember applyClubMember = entityApplyService.applyClub(myMember, saveClub, CLUB_GRADE.LEADER,
        clubMemberInformation).orElseThrow(EntityNotFoundException::new);

    if (saveClub != null) {
      return BaseResponseBodyUtiil.BaseResponseBodySuccess(
          ResponseMessage.SUCCESS_APPLY_CLUB.get());
    }

    return BaseResponseBodyUtiil.BaseResponseBodyFailure(
        ResponseMessage.BAD_APPLY_CLUB.get());
  }
}
