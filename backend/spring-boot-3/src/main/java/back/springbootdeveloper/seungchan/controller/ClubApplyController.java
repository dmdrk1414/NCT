package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.CheckDuplicationClubNameReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.CustomInformation;
import back.springbootdeveloper.seungchan.dto.response.MemberApplyClubResDto;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.CustomClubApplyInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.service.ClubMemberInformationService;
import back.springbootdeveloper.seungchan.service.ClubService;
import back.springbootdeveloper.seungchan.service.CustomClubApplyInformationService;
import back.springbootdeveloper.seungchan.service.EntityApplyService;
import back.springbootdeveloper.seungchan.service.MemberService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "회원이 스터디 팀에 등록하는 컨트롤러", description = "회원의 팀 등록 관련 관리 클래스")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/{club_id}/apply")
public class ClubApplyController {

  private final EntityApplyService entityApplyService;
  private final CustomClubApplyInformationService customClubApplyInformationService;
  private final ClubService clubService;

  @Operation(summary = "동아리 지원서 작성 API - 커스텀 조회")
  @GetMapping("/find")
  public BaseResultDTO<MemberApplyClubResDto> findMemberApplyClub(
      HttpServletRequest request,
      @PathVariable(value = "club_id") Long clubId) {

    List<CustomInformation> customInformations = customClubApplyInformationService
        .findAllCustomInformationByClubId(clubId);
    String clubName = clubService.getClubNameByClubId(clubId);

    return BaseResultDTO.ofSuccess(MemberApplyClubResDto.builder()
        .clubName(clubName)
        .customInformations(customInformations)
        .build());
  }
}
