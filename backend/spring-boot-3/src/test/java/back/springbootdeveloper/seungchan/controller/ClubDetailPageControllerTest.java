package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberReqDto;
import back.springbootdeveloper.seungchan.dto.request.GiveVacationTokenReqDto;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.*;
import back.springbootdeveloper.seungchan.service.ClubGradeService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MoariumSpringBootTest()
class ClubDetailPageControllerTest {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestCreateUtil testCreateUtil;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private ClubMemberInformationRepository clubMemberInformationRepository;
    @Autowired
    private AttendanceStateRepository attendanceStateRepository;
    @Autowired
    private VacationTokenRepository vacationTokenRepository;
    @Autowired
    private ClubGradeService clubGradeService;
    private String token;
    private Member memberOneClubLeader;
    private Long targetClubOneId;

    @BeforeEach
    void setUp() {
        token = testCreateUtil.create_token_two_club_deputy_leader_member();
        memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
        targetClubOneId = testCreateUtil.getONE_CLUB_ID();
    }

    @Test
    void 회원_휴면_페이지_조회() throws Exception {
        // given
        final String url = "/clubs/informations/{club_id}/details/dormancys";
        List<ClubMember> clubMemberDormants
                = clubMemberRepository.findAllByClubIdAndClubGradeId(testCreateUtil.getONE_CLUB_ID(), CLUB_GRADE.DORMANT.getId());

        List<Member> memberDormants = new ArrayList<>();

        clubMemberDormants.forEach(clubMember ->
                memberDormants.add(memberRepository.findById(clubMember.getMemberId()).get()));

        // when
        ResultActions result = mockMvc.perform(get(url, testCreateUtil.getONE_CLUB_ID())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.dormancyMembers", Matchers.hasSize(clubMemberDormants.size())))
                .andExpect(jsonPath("$.result.dormancyMembers[0]").value(memberDormants.get(0).getFullName()))
                .andExpect(jsonPath("$.result.dormancyMembers[1]").value(memberDormants.get(1).getFullName()));
    }

    @Test
    void 회원_상세_조회_테스트() throws Exception {
        // given
        final String url = "/clubs/informations/{club_id}/details/{club_member_id}";
        final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId, memberOneClubLeader.getMemberId()).get();
        final ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(clubMember.getClubMemberInformationId()).get();

        final String targetName = memberOneClubLeader.getFullName();
        final String targetMajor = memberOneClubLeader.getMajor();
        final String targetStudentId = memberOneClubLeader.getStudentId();
        final String targetSelfIntroduction = clubMemberInformation.getIntroduce();

        // when
        ResultActions result = mockMvc.perform(get(url, targetClubOneId, clubMember.getClubMemberId())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.name").value(targetName))
                .andExpect(jsonPath("$.result.major").value(targetMajor))
                .andExpect(jsonPath("$.result.studentId").value(targetStudentId))
                .andExpect(jsonPath("$.result.selfIntroduction").value(targetSelfIntroduction));
    }

    @Test
    void 동아리_소개_페이지_휴가_제공_테스트() throws Exception {
        // given
        final String token = testCreateUtil.create_token_one_club_leader_member();
        final String url = "/clubs/informations/{club_id}/details/{club_member_id}/vacation";

        final Member targetMember = memberOneClubLeader;
        final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId, targetMember.getMemberId()).get();
        final AttendanceState targetAttendanceState = attendanceStateRepository.findById(targetClubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);
        final VacationToken targetVacationToken = targetAttendanceState.getVacationToken();
        final Integer targetTokenCount = 5;
        final GiveVacationTokenReqDto requestDto = GiveVacationTokenReqDto.builder()
                .vacationToken(targetTokenCount)
                .build();

        // when
        final String requestBody = objectMapper.writeValueAsString(requestDto);
        ResultActions result = mockMvc.perform(post(url, targetClubOneId, targetClubMember.getClubMemberId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        final AttendanceState updateAttendanceState = attendanceStateRepository.findById(targetClubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);
        final VacationToken updateVacationToken = updateAttendanceState.getVacationToken();

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_MESSAGE_VALUE.SUCCESS_UPDATE_VACATION_TOKEN(targetMember.getFullName(), targetTokenCount)));

        assertThat(updateVacationToken.getVacationToken()).isEqualTo(targetVacationToken.getVacationToken() + targetTokenCount);
    }
}