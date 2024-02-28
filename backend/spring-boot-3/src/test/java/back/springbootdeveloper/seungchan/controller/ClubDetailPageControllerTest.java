package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubMemberInformationRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.service.MemberService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MoariumSpringBootTest()
class ClubDetailPageControllerTest {
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
    private String token;
    private Member memberOneClubLeader;
    private Long clubOneId;

    @BeforeEach
    void setUp() {
        token = testCreateUtil.create_token_two_club_deputy_leader_member();
        memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
        clubOneId = testCreateUtil.getONE_CLUB_ID();
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
        final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubOneId, memberOneClubLeader.getMemberId()).get();
        final ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(clubMember.getClubMemberInformationId()).get();

        final String targetName = memberOneClubLeader.getFullName();
        final String targetMajor = memberOneClubLeader.getMajor();
        final String targetStudentId = memberOneClubLeader.getStudentId();
        final String targetSelfIntroduction = clubMemberInformation.getIntroduce();
        
        // when
        ResultActions result = mockMvc.perform(get(url, clubOneId, clubMember.getClubMemberId())
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
}