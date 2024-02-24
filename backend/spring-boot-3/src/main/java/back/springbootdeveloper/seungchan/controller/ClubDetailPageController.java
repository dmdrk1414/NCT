package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.ClubMemberDetailResDto;
import back.springbootdeveloper.seungchan.dto.response.ClubMemberInformationResDto;
import back.springbootdeveloper.seungchan.dto.response.ClubMemberResponse;
import back.springbootdeveloper.seungchan.dto.response.DormancysMembersResDto;
import back.springbootdeveloper.seungchan.service.ClubDetailPageService;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "club detail page 컨트롤러", description = "club datail page 컨트롤러 - 토큰 필요")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/informations/{club_id}/details")
public class ClubDetailPageController {
    private final ClubDetailPageService clubDetailPageService;

    @Autowired
    public ClubDetailPageController(ClubDetailPageService clubDetailPageService) {
        this.clubDetailPageService = clubDetailPageService;
    }

    @Operation(summary = "회원 휴먼 페이지 조회", description = "해당 클럽의 휴먼 회원들 조회")
    @GetMapping(value = "/dormancys")
    public BaseResultDTO<DormancysMembersResDto> getDormancysMemberPage(@PathVariable(value = "club_id") Long clubId) {
        List<String> allDormancyMemberNamesOfClub = clubDetailPageService.getAllDormancyMemberNamesOfClub(clubId);
        DormancysMembersResDto dormancysMembersResDto = DormancysMembersResDto.builder()
                .dormancyMembers(allDormancyMemberNamesOfClub)
                .build();

        return BaseResultDTO.ofSuccess(dormancysMembersResDto);
    }

    @Operation(summary = "동아리 상세 페이지 조회", description = "동아리 휴먼 회원들 제외한 모든 회원들 상세 조회 가능")
    @GetMapping(value = "")
    public BaseResultDTO<ClubMemberDetailResDto> getMemberDetailsPage(@PathVariable(value = "club_id") Long clubId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        ClubMemberDetailResDto clubMemberResponse = clubDetailPageService.getClubMemberResponse(clubId, memberId);

        return BaseResultDTO.ofSuccess(clubMemberResponse);
    }

    @Operation(summary = "회원 상세 조회", description = "동아리 특정 회원 정보 상세 조회 가능")
    @GetMapping(value = "/{club_member_id}")
    public BaseResultDTO<ClubMemberInformationResDto> getClubMemberDetails(@PathVariable(value = "club_id") Long clubId,
                                                                           @PathVariable(value = "club_member_id") Long clubMemberId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        ClubMemberInformationResDto clubMemberResponse = clubDetailPageService.getClubMemberInformationResDto(memberId, clubMemberId);

        return BaseResultDTO.ofSuccess(clubMemberResponse);
    }
}