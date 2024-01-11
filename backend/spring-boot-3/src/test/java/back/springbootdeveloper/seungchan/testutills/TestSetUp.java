package back.springbootdeveloper.seungchan.testutills;

import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestSetUp {
    private final UserService userService;
    private final UserUtillService userUtillService;
    private final AttendanceService attendanceService;
    private final AttendanceTimeService attendanceTimeService;
    private final PeriodicDataService periodicDataService;
    @Autowired
    private TokenService tokenService;
    private Long kingUserId;
    private Long userId_1;
    private String passwordOfKingUser;


    @Autowired
    public TestSetUp(UserService userService, UserUtillService userUtillService, AttendanceService attendanceService, AttendanceTimeService attendanceTimeService, PeriodicDataService periodicDataService) {
        this.userService = userService;
        this.userUtillService = userUtillService;
        this.attendanceService = attendanceService;
        this.attendanceTimeService = attendanceTimeService;
        this.periodicDataService = periodicDataService;
    }

    /**
     * 실장 유저를 설정한다.
     */
    public UserInfo setUpKingUser() {
        // userID 1
        UserInfo user = userService.save(TestMakeObject.makeUser("실장 유저", "seungchan141414@gmail.com"));
        kingUserId = user.getId();


        userUtillService.save(TestMakeObject.makeUserUtill(user, 0, true));

        String vacationDates = "2023-08-01, 2023-08-07, 2023-08-14";
        String absenceDates = "2023-08-15";
        String weeklyData = "[0,0,0,0,0]";
        attendanceService.save(TestMakeObject.makeAttendanceStatus(user, vacationDates, absenceDates, weeklyData));

        AttendanceTime attendanceTime = new AttendanceTime(user);
        attendanceTimeService.save(attendanceTime);


        String basicWeeklyData = "[0,0,0,0,0]";
        String basicMonth = "";
        PeriodicData periodicDataOfNewUser = PeriodicData.builder()
                .userId(user.getId())
                .name(user.getName())
                .weeklyData(basicWeeklyData)
                .thisMonth(basicMonth)
                .previousMonth(basicMonth)
                .build();
        periodicDataService.save(periodicDataOfNewUser);

        return user;
    }

    /**
     * 테스트 유저를 위한 설정
     */
    public UserInfo setUpUser() {
        // userID 1
        UserInfo user = userService.save(TestMakeObject.makeUser("일반 유저", "nomal@gmail.com"));
        userId_1 = user.getId();

        userUtillService.save(TestMakeObject.makeUserUtill(user, 5, false));

        String vacationDates = "";
        String absenceDates = "";
        String weeklyData = "[0,0,0,0,0]";
        attendanceService.save(TestMakeObject.makeAttendanceStatus(user, vacationDates, absenceDates, weeklyData));

        AttendanceTime attendanceTime = new AttendanceTime(user);
        attendanceTimeService.save(attendanceTime);


        String basicWeeklyData = "[0,0,0,0,0]";
        String basicMonth = "";
        PeriodicData periodicDataOfNewUser = PeriodicData.builder()
                .userId(user.getId())
                .name(user.getName())
                .weeklyData(basicWeeklyData)
                .thisMonth(basicMonth)
                .previousMonth(basicMonth)
                .build();
        periodicDataService.save(periodicDataOfNewUser);

        return user;
    }

    public UserInfo setUpOldUser() {
        // userId OB 2
        UserInfo userOb = userService.save(TestMakeObject.makeUserOb("졸업 유저", "2@gmail.com"));

        return userOb;
    }

    /**
     * 테스트를 위한 임시 토큰을 생성한다.
     *
     * @return
     */
    public String getToken(MockMvc mockMvc) {
        UserInfo kingUser = userService.save(this.setUpKingUser());

        String url = "/login";
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        try {
            request = mockMvc.perform(
                    post(url).param("email", kingUser.getEmail()).param("password", kingUser.getPassword())
            ).andReturn().getRequest();

            response = mockMvc.perform(
                    post(url).param("email", kingUser.getEmail()).param("password", kingUser.getPassword())
            ).andReturn().getResponse();

        } catch (Exception e) {
            System.out.println("AttendanceControllerTest : 토큰 생성과정 throw new RuntimeException(e);");
        }

        return tokenService.createAccessAndRefreshToken(request, response, kingUser.getEmail());
    }

    public String getKingUserPassword() {
        return TestMakeObject.TEST_PASSWORD;
    }

    public Long getKingUserId() {
        return this.kingUserId;
    }

    public Long getUserId_1() {
        return this.userId_1;
    }

}
