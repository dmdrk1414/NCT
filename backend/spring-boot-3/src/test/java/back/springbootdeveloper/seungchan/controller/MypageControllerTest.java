package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.dto.request.TempUserFormReqDto;
import back.springbootdeveloper.seungchan.dto.request.UpdateUserFormReqDto;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class MypageControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private TestSetUp testSetUp;
    @Autowired
    private UserService userService;
    private String token;
    private Long kingUserId;
    private UserInfo kingUser;

    @BeforeEach
    void setUp() {
        databaseService.deleteAllDatabase();
        token = testSetUp.getToken(mockMvc);
        kingUserId = testSetUp.getKingUserId();
        kingUser = userService.findUserById(kingUserId);
    }

    @Test
    void 현제_회원_자신의_정보_조회_테스트() throws Exception {
        // given
        final String url = "/mypage";

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result
                .andExpect(jsonPath("$.name").value(kingUser.getName()))
                .andExpect(jsonPath("$.phoneNum").value(kingUser.getPhoneNum()))
                .andExpect(jsonPath("$.major").value(kingUser.getMajor()))
                .andExpect(jsonPath("$.gpa").value(kingUser.getGpa()))
                .andExpect(jsonPath("$.address").value(kingUser.getAddress()))
                .andExpect(jsonPath("$.specialtySkill").value(kingUser.getSpecialtySkill()))
                .andExpect(jsonPath("$.hobby").value(kingUser.getHobby()))
                .andExpect(jsonPath("$.mbti").value(kingUser.getMbti()))
                .andExpect(jsonPath("$.studentId").value(kingUser.getStudentId()))
                .andExpect(jsonPath("$.birthDate").value(kingUser.getBirthDate()))
                .andExpect(jsonPath("$.advantages").value(kingUser.getAdvantages()))
                .andExpect(jsonPath("$.disadvantage").value(kingUser.getDisadvantage()))
                .andExpect(jsonPath("$.selfIntroduction").value(kingUser.getSelfIntroduction()))
                .andExpect(jsonPath("$.photo").value(kingUser.getPhoto()))
                .andExpect(jsonPath("$.yearOfRegistration").value(kingUser.getYearOfRegistration()))
                .andExpect(jsonPath("$.ob").value(kingUser.isOb()));
    }

    @Test
    public void 현제_회원_본인_정보_업데이트_테스트() throws Exception {
        // given
        final String url = "/mypage/update";
        String nameBefore = kingUser.getName();
        kingUser.setName("변경_이름");

        UpdateUserFormReqDto request = new UpdateUserFormReqDto(
                kingUser.getName(),
                kingUser.getPhoneNum(),
                kingUser.getMajor(),
                kingUser.getGpa(),
                kingUser.getAddress(),
                kingUser.getSpecialtySkill(),
                kingUser.getHobby(),
                kingUser.getMbti(),
                kingUser.getStudentId(),
                kingUser.getBirthDate(),
                kingUser.getAdvantages(),
                kingUser.getDisadvantage(),
                kingUser.getSelfIntroduction(),
                kingUser.getPhoto(),
                kingUser.getEmail()
        );

        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        UserInfo target = userService.findUserById(kingUser.getId());

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
        assertThat(target.getName()).isEqualTo(kingUser.getName());
        assertThat(target.getName()).isNotEqualTo(nameBefore);
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "name",
            "phoneNum",
            "major",
            "gpa",
            "address",
            "specialtySkill",
            "hobby",
            "mbti",
            "studentId",
            "birthDate",
            "advantages",
            "disadvantage",
            "selfIntroduction",
            "email",
    })
    public void 현제_회원_본인_정보_업데이트_null_예외_테스트(String check) throws Exception {
        // given
        final String url = "/mypage/update";
        String nameBefore = kingUser.getName();
        kingUser.setName(nameBefore);

        UpdateUserFormReqDto request = getUpdateUserFormReqDto();
        switch (check) {
            case "name":
                request.setName(null);
                break;
            case "phoneNum":
                request.setPhoneNum(null);
                break;
            case "major":
                request.setMajor(null);
                break;
            case "gpa":
                request.setGpa(null);
                break;
            case "address":
                request.setAddress(null);
                break;
            case "specialtySkill":
                request.setSpecialtySkill(null);
                break;
            case "hobby":
                request.setHobby(null);
                break;
            case "mbti":
                request.setMbti(null);
                break;
            case "studentId":
                request.setStudentId(null);
                break;
            case "birthDate":
                request.setBirthDate(null);
                break;
            case "advantages":
                request.setAdvantages(null);
                break;
            case "disadvantage":
                request.setDisadvantage(null);
                break;
            case "selfIntroduction":
                request.setSelfIntroduction(null);
                break;
            case "email":
                request.setEmail(null);
                break;
        }
        MockHttpServletResponse response = getResponse(request);

        // JSON 응답을 Map으로 변환
        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);

        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
    }

    private MockHttpServletResponse getResponse(UpdateUserFormReqDto request) throws Exception {
        final String requestBody = objectMapper.writeValueAsString(request);
        final String url = "/mypage/update";

        MvcResult result = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();

        return result.getResponse();
    }

    private UpdateUserFormReqDto getUpdateUserFormReqDto() {
        return new UpdateUserFormReqDto(
                kingUser.getName(),
                kingUser.getPhoneNum(),
                kingUser.getMajor(),
                kingUser.getGpa(),
                kingUser.getAddress(),
                kingUser.getSpecialtySkill(),
                kingUser.getHobby(),
                kingUser.getMbti(),
                kingUser.getStudentId(),
                kingUser.getBirthDate(),
                kingUser.getAdvantages(),
                kingUser.getDisadvantage(),
                kingUser.getSelfIntroduction(),
                kingUser.getPhoto(),
                kingUser.getEmail()
        );
    }
}
