package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.controller.config.jwt.JwtFactory;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import back.springbootdeveloper.seungchan.testutil.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@MoariumSpringBootTest()
@SpringBootTest()
@AutoConfigureMockMvc
class ClubDetailPageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestCreateUtil testCreateUtil;
    private String token;

    @BeforeEach
    void setUp() {
        token = testCreateUtil.createToken(1L);
    }

    @Test
    void getDormancysMemberPage() throws Exception {

        final String url = "/clubs/informations/1/details/dormancys";

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.result.dormancyMembers").value(attendanceListFromJson.getAbsences()));
    }
}