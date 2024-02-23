package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.ClubMemberDetailResDto;
import back.springbootdeveloper.seungchan.dto.response.ClubMemberResponse;
import back.springbootdeveloper.seungchan.dto.response.DormancysMembersResDto;
import back.springbootdeveloper.seungchan.service.ClubDetailPageService;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
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

    @GetMapping(value = "/dormancys")
    public BaseResultDTO<DormancysMembersResDto> getDormancysMemberPage(@PathVariable(value = "club_id") Long clubId) {
        List<String> allDormancyMemberNamesOfClub = clubDetailPageService.getAllDormancyMemberNamesOfClub(clubId);
        DormancysMembersResDto dormancysMembersResDto = DormancysMembersResDto.builder()
                .dormancyMembers(allDormancyMemberNamesOfClub)
                .build();

        return BaseResultDTO.ofSuccess(dormancysMembersResDto);
    }

    @GetMapping(value = "")
    public BaseResultDTO<ClubMemberDetailResDto> getMemberDetailsPage(@PathVariable(value = "club_id") Long clubId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        ClubMemberDetailResDto clubMemberResponse = clubDetailPageService.getClubMemberResponse(clubId, memberId);

        return BaseResultDTO.ofSuccess(clubMemberResponse);
    }
}