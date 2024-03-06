package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.CheckDuplicationClubNameReqDto;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.service.ClubService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

@MoariumSpringBootTest
class ClubRegistrationControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private ClubRegistrationController controller;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
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

//  @Test
//  void 개인_회원_팀_등록_테스트() throws Exception {
//    final String url = "/main/club/apply";
//    String clubName = "테스트 팀 이름";
//    String clubIntroduce = "테스트 팀 자기소개";
//
//    // 테스트용 클럽 등록 요청 데이터 생성
//    ClubRegistrationReqDto requestDto = new ClubRegistrationReqDto();
//    requestDto.setClubName(clubName);
//    requestDto.setClubIntroduction(clubIntroduce);
//
//    // 테스트용 클럽 프로필 이미지 생성
//    MockMultipartFile clubProfileImage = new MockMultipartFile("clubProfileImage", "profile.jpg",
//        MediaType.IMAGE_JPEG_VALUE, "profile image".getBytes());
//
//    // 테스트용 클럽 정보 이미지들 생성
//    List<MockMultipartFile> infoImages = new ArrayList<>();
//    infoImages.add(new MockMultipartFile("clubInformationImages", "info1.jpg",
//        MediaType.IMAGE_JPEG_VALUE, "info image 1".getBytes()));
//    infoImages.add(new MockMultipartFile("clubInformationImages", "info2.jpg",
//        MediaType.IMAGE_JPEG_VALUE, "info image 2".getBytes()));
//    infoImages.add(new MockMultipartFile("clubInformationImages", "info3.jpg",
//        MediaType.IMAGE_JPEG_VALUE, "info image 3".getBytes()));
//
//
//  }

  @Test
  void 팀이름_중복_검사_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/main/club/apply/overlap";
    final Member targetMember = memberOneClubLeader;

    // 검증 준비
    final String targetClubName = "테스트를 위한 클럽 이름";
    final CheckDuplicationClubNameReqDto requestDto = CheckDuplicationClubNameReqDto.builder()
        .clubName(targetClubName)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE_VALUE.SUCCESS_NOT_DUPLICATION_CLUBNAME(targetClubName)));
  }

  @Test
  void 팀이름_중복_검사_예외_중복_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/main/club/apply/overlap";
    final Member targetMember = memberOneClubLeader;

    // 검증 준비
    final String targetClubName = clubRepository.findAll().get(0).getClubName();
    final CheckDuplicationClubNameReqDto requestDto = CheckDuplicationClubNameReqDto.builder()
        .clubName(targetClubName)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_DUPLICATION_CLUBNAME.get()));
  }
}