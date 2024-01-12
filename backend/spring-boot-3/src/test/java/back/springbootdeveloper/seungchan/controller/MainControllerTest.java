package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.service.*;
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
    @Autowired
    private AttendanceTimeService attendanceTimeService;
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

    @Test
    public void 메인페이지_각_유저별_상세_조회_테스트_1() throws Exception {
        // given
        final String url = "/main/detail/{id}";

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, kingUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );


        // then
        resultActions
                .andExpect(jsonPath("$.name").value(kingUser.getName()))
                .andExpect(jsonPath("$.major").value(kingUser.getMajor()))
                .andExpect(jsonPath("$.studentId").value(kingUser.getStudentId()))
                .andExpect(jsonPath("$.phoneNum").value(kingUser.getPhoneNum()))
                .andExpect(jsonPath("$.hobby").value(kingUser.getHobby()))
                .andExpect(jsonPath("$.specialtySkill").value(kingUser.getSpecialtySkill()))
                .andExpect(jsonPath("$.mbti").value(kingUser.getMbti()))
                .andExpect(jsonPath("$.userId").value(kingUser.getId()))
                .andExpect(jsonPath("$.ob").value(kingUser.isOb()))
                .andExpect(jsonPath("$.nuriKing").value(true));
    }

    @Test
    public void 메인페이지_각_유저별_상세_조회_테스트_2() throws Exception {
        // given
        final String url = "/main/detail/{id}";

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, nomalUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        resultActions
                .andExpect(jsonPath("$.name").value(nomalUser.getName()))
                .andExpect(jsonPath("$.major").value(nomalUser.getMajor()))
                .andExpect(jsonPath("$.studentId").value(nomalUser.getStudentId()))
                .andExpect(jsonPath("$.phoneNum").value(nomalUser.getPhoneNum()))
                .andExpect(jsonPath("$.hobby").value(nomalUser.getHobby()))
                .andExpect(jsonPath("$.specialtySkill").value(nomalUser.getSpecialtySkill()))
                .andExpect(jsonPath("$.mbti").value(nomalUser.getMbti()))
                .andExpect(jsonPath("$.userId").value(nomalUser.getId()))
                .andExpect(jsonPath("$.ob").value(nomalUser.isOb()))
                .andExpect(jsonPath("$.nuriKing").value(true));
    }

    @Test
    void 회원_개인_각각_출석시간_찾기_테스트() throws Exception {
        final String url = "/main/detail/{id}/control";
        AttendanceTime attendanceTime = attendanceTimeService.findByUserId(kingUser.getId());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, kingUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(attendanceTime.getName()))
                .andExpect(jsonPath("$.mondayAttendanceTime").value(attendanceTime.getMonday()))
                .andExpect(jsonPath("$.tuesdayAttendanceTime").value(attendanceTime.getTuesday()))
                .andExpect(jsonPath("$.wednesdayAttendanceTime").value(attendanceTime.getWednesday()))
                .andExpect(jsonPath("$.thursdayAttendanceTime").value(attendanceTime.getThursday()))
                .andExpect(jsonPath("$.fridayAttendanceTime").value(attendanceTime.getFriday()));
    }
}

