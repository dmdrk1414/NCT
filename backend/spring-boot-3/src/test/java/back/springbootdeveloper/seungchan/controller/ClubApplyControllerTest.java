package back.springbootdeveloper.seungchan.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.dto.response.CustomInformation;
import back.springbootdeveloper.seungchan.entity.AttendanceNumber;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubControl;
import back.springbootdeveloper.seungchan.entity.CustomClubApplyInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.service.CustomClubApplyInformationService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@MoariumSpringBootTest
class ClubApplyControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubRepository clubRepository;
  @Autowired
  private CustomClubApplyInformationService customClubApplyInformationService;

  private Member memberOneClubLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 동아리_지원서_작성_API_커스텀_조회() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/{club_id}/apply/find";
    final Club targetClub = clubRepository.findById(targetClubOneId).get();

    // 검증을 위한 데이터 준비
    final String targetClubName = targetClub.getClubName();
    List<CustomInformation> customInformations = customClubApplyInformationService
        .findAllCustomInformationByClubId(targetClub.getClubId());

    // when
    ResultActions result = mockMvc.perform(get(url, targetClub.getClubId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.clubName").value(
            targetClub.getClubName()));
    for (int i = 0; i < customInformations.size(); i++) {

      result
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customInformationId").value(
              customInformations.get(i).getCustomInformationId()))
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customContent").value(
              customInformations.get(i).getCustomContent()))
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customType").value(
              customInformations.get(i).getCustomType()));
    }
  }
}