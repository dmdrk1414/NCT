package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.service.AttendanceService;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class MainControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private TestSetUp testSetUp;
    @Autowired
    private UserUtillService userUtillService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttendanceService attendanceStateService;
    private String token;
    private Long kingUserId;
    private UserInfo kingUser;
    private UserUtill userUtillOfKingUser;
    private UserInfo nomalUser;
    private UserUtill userUtillOfNomalUser;
    private AttendanceStatus attendanceStatusOfKingUser;
    private AttendanceStatus attendanceStatusOfNomalUser;
    private UserInfo obUser;

    @BeforeEach
    void setUp() {
        databaseService.deleteAllDatabase();
        token = testSetUp.getToken(mockMvc);
        kingUserId = testSetUp.getKingUserId();
        kingUser = userService.findUserById(kingUserId);
        nomalUser = testSetUp.setUpUser();
        userUtillOfKingUser = userUtillService.findUserByUserId(kingUser.getId());
        userUtillOfNomalUser = userUtillService.findUserByUserId(nomalUser.getId());
        attendanceStatusOfKingUser = attendanceStateService.findById(kingUser.getId());
        attendanceStatusOfNomalUser = attendanceStateService.findById(nomalUser.getId());
        obUser = testSetUp.setUpOldUser();
    }


    @Test
    void 메인페이지_현재_회원들의_정보를_찾는다() throws Exception {
        // given
        final String url = "/main/ybs";


        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ybUserInfomationList[0].name").value(kingUser.getName()))
                .andExpect(jsonPath("$.ybUserInfomationList[0].cntVacation").value(userUtillOfKingUser.getCntVacation()))
                .andExpect(jsonPath("$.ybUserInfomationList[0].weeklyData").value(TestUtills.StringToListFromAttendanceStateWeeklyDate(attendanceStatusOfKingUser.getWeeklyData())))
                .andExpect(jsonPath("$.ybUserInfomationList[1].name").value(nomalUser.getName()))
                .andExpect(jsonPath("$.ybUserInfomationList[1].cntVacation").value(userUtillOfNomalUser.getCntVacation()))
                .andExpect(jsonPath("$.ybUserInfomationList[1].weeklyData").value(TestUtills.StringToListFromAttendanceStateWeeklyDate(attendanceStatusOfNomalUser.getWeeklyData())))
                .andExpect(jsonPath("$.passAttendanceOfSearchUse").value(false));

    }

    @Test
    void 메인페이지_졸업_회원들의_정보를_찾는다() throws Exception {
        // given
        final String url = "/main/obs";

        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        resultActions
                .andExpect(jsonPath("$[0].obUserList[0].name").value(obUser.getName()))
                .andExpect(jsonPath("$[0].obUserList[0].yearOfRegistration").value(obUser.getYearOfRegistration()))
                .andExpect(jsonPath("$[0].obUserList[0].phoneNum").value(obUser.getPhoneNum()))
                .andExpect(jsonPath("$[0].obUserList[0].userId").value(obUser.getId()));
    }
}