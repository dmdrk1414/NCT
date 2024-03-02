package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.UpdateClubArticlePutDto;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubArticleRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.service.ClubArticleService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@MoariumSpringBootTest
class ClubArticleControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubRepository clubRepository;
  @Autowired
  private ClubArticleRepository clubArticleRepository;
  @Autowired
  private ClubMemberRepository clubMemberRepository;
  @Autowired
  private ClubArticleService clubArticleService;
  private Member memberOneClubLeader;
  private Member memberOneClubDeputyLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    memberOneClubDeputyLeader = testCreateUtil.get_entity_one_club_deputy_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 팀_게시판_수정_API_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final String updateTitle = "테스트를 위한 업데이트 클럽게시물 제목";
    final String updateContent = "테스트를 위한 업데이트 클럽게시물 내용";
    final UpdateClubArticlePutDto requestDto = UpdateClubArticlePutDto.builder()
        .clubArticleUpdateTitle(updateTitle)
        .clubArticleUpdateContent(updateContent)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        put(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleRepository.findById(
        targetClubArticle.getClubArticleId()).get();

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.UPDATE_CLUB_ARTICLE.get()))
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultClubArticle.getTitle()).isEqualTo(updateTitle);
    assertThat(resultClubArticle.getContent()).isEqualTo(updateContent);
  }

  @Test
  void 팀_게시판_수정_API_예외_게시판_저자_검사_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubDeputyLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final String updateTitle = "테스트를 위한 업데이트 클럽게시물 제목";
    final String updateContent = "테스트를 위한 업데이트 클럽게시물 내용";
    final UpdateClubArticlePutDto requestDto = UpdateClubArticlePutDto.builder()
        .clubArticleUpdateTitle(updateTitle)
        .clubArticleUpdateContent(updateContent)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        put(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_UPDATE_CLUB_ARTICLE.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 팀_게시판_삭제_API_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());
    Boolean resultDelete = false;

    // when
    ResultActions result = mockMvc.perform(
        delete(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle deleteClubArticle;
    try {
      deleteClubArticle = clubArticleRepository.findById(targetClubArticle.getClubArticleId())
          .get();
    } catch (NoSuchElementException e) {
      resultDelete = true;
    }

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.SUCCESS_DELETE_CLUB_ARTICLE.get()))
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultDelete).isTrue();
  }
}