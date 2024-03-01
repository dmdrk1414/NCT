package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.dto.request.GiveVacationTokenReqDto;
import back.springbootdeveloper.seungchan.entity.AttendanceState;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.entity.VacationToken;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MoariumSpringBootTest
class MyPageControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubMemberRepository clubMemberRepository;
  @Autowired
  private ClubRepository clubRepository;
  private Member memberOneClubLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 마이페이지_동아리_탈퇴하기_테스트() throws Exception {
    // given
    // 유저 로그인
    token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/quit";
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByMemberId(
        targetMember.getMemberId()).get();
    final Club targetClub = clubRepository.findById(targetClubMember.getClubId()).get();
    Boolean resultDelete = false;

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubMember deleteClubMember;
    try {
      clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
          targetMember.getMemberId()).get();
    } catch (NoSuchElementException e) {
      resultDelete = true;
    }

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE_VALUE.SUCCESS_QUIT_CLUB(targetClub.getClubName())));

    assertThat(resultDelete).isTrue();
  }
}