package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
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
    private String token;
    private Member memberOneClubLeader;

    @BeforeEach
    void setUp() {
        token = testCreateUtil.create_token_two_club_deputy_leader_member();
        memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    }

    @Test
    void 회원_휴면_페이지_조회() throws Exception {
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
}