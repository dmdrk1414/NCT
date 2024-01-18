package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.service.*;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class NewUserControllerTest {
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
    @Autowired
    private TempUserService tempUserService;
    @Autowired
    private UserUtillService userUtillService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private PeriodicDataService periodicDataService;
    @Autowired
    private AttendanceTimeService attendanceTimeService;
    private String token;
    private Long kingUserId;
    private UserInfo kingUser;

    @BeforeEach
    void setUp() {
        databaseService.deleteAllDatabase();
        token = testSetUp.getToken(mockMvc);
        kingUserId = testSetUp.getKingUserId();
        kingUser = userService.findUserById(kingUserId);
        testSetUp.getTempUsers();
    }

    @Test
    void 모든_신청한_유저_정보_반환_테스트() throws Exception {
        List<TempUser> tempUserList = tempUserService.findAll();
        final String url = "/new-users";
        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(tempUserList.get(0).getEmail()))
                .andExpect(jsonPath("$[0].name").value(tempUserList.get(0).getName()))
                .andExpect(jsonPath("$[1].email").value(tempUserList.get(1).getEmail()))
                .andExpect(jsonPath("$[1].name").value(tempUserList.get(1).getName()))
                .andExpect(jsonPath("$[2].email").value(tempUserList.get(2).getEmail()))
                .andExpect(jsonPath("$[2].name").value(tempUserList.get(2).getName()));
    }


    @Test
    void 개별_신청_유저_정보_반환_테스트_1() throws Exception {
        final String url = "/new-users/{id}";
        List<TempUser> tempUsers = tempUserService.findAll();
        TempUser tempUser = tempUsers.get(0);

        // when
        ResultActions resultActions = mockMvc.perform(get(url, tempUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tempUser.name").value(tempUser.getName()))
                .andExpect(jsonPath("$.tempUser.phoneNum").value(tempUser.getPhoneNum()))
                .andExpect(jsonPath("$.tempUser.major").value(tempUser.getMajor()))
                .andExpect(jsonPath("$.tempUser.gpa").value(tempUser.getGpa()))
                .andExpect(jsonPath("$.tempUser.email").value(tempUser.getEmail()));
    }

    @Test
    void 개별_신청_유저_정보_반환_테스트_2() throws Exception {
        final String url = "/new-users/{id}";
        List<TempUser> tempUsers = tempUserService.findAll();
        TempUser tempUser = tempUsers.get(1);

        // when
        ResultActions resultActions = mockMvc.perform(get(url, tempUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tempUser.name").value(tempUser.getName()))
                .andExpect(jsonPath("$.tempUser.phoneNum").value(tempUser.getPhoneNum()))
                .andExpect(jsonPath("$.tempUser.major").value(tempUser.getMajor()))
                .andExpect(jsonPath("$.tempUser.gpa").value(tempUser.getGpa()))
                .andExpect(jsonPath("$.tempUser.email").value(tempUser.getEmail()));
    }

    @Test
    void 실장_신청_인원_승락_테스트_1() throws Exception {
        final String url = "/new-users/{id}/acceptance";
        List<TempUser> tempUsers = tempUserService.findAll();
        TempUser tempUser = tempUsers.get(0);

        // when
        ResultActions result = mockMvc.perform(post(url, tempUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        UserInfo userInfo = userService.findByEmail(tempUser.getEmail());
        UserUtill userUtill = userUtillService.findUserByUserId(userInfo.getId());
        AttendanceStatus attendanceStatus = attendanceService.findById(userInfo.getId());
        PeriodicData periodicData = periodicDataService.finByUserId(userInfo.getId());
        AttendanceTime attendanceTime = attendanceTimeService.findByUserId(userInfo.getId());

        // than
        result
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

        assertThat(userInfo).isNotNull();
        assertThat(userUtill).isNotNull();
        assertThat(attendanceStatus).isNotNull();
        assertThat(periodicData).isNotNull();
        assertThat(attendanceTime).isNotNull();

        assertThat(userInfo.getEmail()).isEqualTo(tempUser.getEmail());
        assertThat(userInfo.getName()).isEqualTo(tempUser.getName());
        assertThat(userInfo.getPhoneNum()).isEqualTo(tempUser.getPhoneNum());
    }

    @Test
    void 실장_신청_인원_승락_테스트_2() throws Exception {
        final String url = "/new-users/{id}/acceptance";
        List<TempUser> tempUsers = tempUserService.findAll();
        TempUser tempUser = tempUsers.get(1);

        // when
        ResultActions result = mockMvc.perform(post(url, tempUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        UserInfo userInfo = userService.findByEmail(tempUser.getEmail());
        UserUtill userUtill = userUtillService.findUserByUserId(userInfo.getId());
        AttendanceStatus attendanceStatus = attendanceService.findById(userInfo.getId());
        PeriodicData periodicData = periodicDataService.finByUserId(userInfo.getId());
        AttendanceTime attendanceTime = attendanceTimeService.findByUserId(userInfo.getId());

        // than
        result
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

        assertThat(userInfo).isNotNull();
        assertThat(userUtill).isNotNull();
        assertThat(attendanceStatus).isNotNull();
        assertThat(periodicData).isNotNull();
        assertThat(attendanceTime).isNotNull();

        assertThat(userInfo.getEmail()).isEqualTo(tempUser.getEmail());
        assertThat(userInfo.getName()).isEqualTo(tempUser.getName());
        assertThat(userInfo.getPhoneNum()).isEqualTo(tempUser.getPhoneNum());
    }

    @Test
    void 신청_실원_거절_테스트() throws Exception {
        // given
        final String url = "/new-users/{id}/reject";
        List<TempUser> targets = tempUserService.findAll();
        TempUser target = targets.get(0);

        // when
        ResultActions result = mockMvc.perform(post(url, target.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        List<TempUser> results = tempUserService.findAll();
        assertThat(results.contains(target)).isFalse();

        // than
        result
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    }
}