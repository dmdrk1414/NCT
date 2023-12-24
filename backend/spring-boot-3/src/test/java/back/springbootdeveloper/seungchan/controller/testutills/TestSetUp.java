package back.springbootdeveloper.seungchan.controller.testutills;

import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSetUp {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserUtilRepository userUtilRepository;
    @Autowired
    private AttendanceStatusRepository attendanceStatusRepository;
    @Autowired
    private AttendanceTimeRepository attendanceTimeRepository;
    @Autowired
    private PeriodicDataRepository periodicDataRepository;


    /**
     * 실장 유저를 설정한다.
     */
    public void setUpKingUser() {
        // userID 1
        UserInfo user = userRepository.save(TestMakeObject.makeUser("실장 유저", "seungchan141414@gmail.com"));


        userUtilRepository.save(TestMakeObject.makeUserUtill(user, 0, true));

        String vacationDates = "2023-08-01, 2023-08-07, 2023-08-14";
        String absenceDates = "2023-08-15";
        String weeklyData = "[0,0,0,0,0]";
        attendanceStatusRepository.save(TestMakeObject.makeAttendanceStatus(user, vacationDates, absenceDates, weeklyData));

        AttendanceTime attendanceTime = new AttendanceTime(user);
        attendanceTimeRepository.save(attendanceTime);


        String basicWeeklyData = "[0,0,0,0,0]";
        String basicMonth = "";
        PeriodicData periodicDataOfNewUser = PeriodicData.builder()
                .userId(user.getId())
                .name(user.getName())
                .weeklyData(basicWeeklyData)
                .thisMonth(basicMonth)
                .previousMonth(basicMonth)
                .build();
        periodicDataRepository.save(periodicDataOfNewUser);
    }

    /**
     * 테스트 유저를 위한 설정
     */
    public void setUpUser() {
        // userID 1
        UserInfo user = userRepository.save(TestMakeObject.makeUser("일반 유저", "seungchan141414@gmail.com"));

        userUtilRepository.save(TestMakeObject.makeUserUtill(user, 5, false));

        String vacationDates = "";
        String absenceDates = "";
        String weeklyData = "[0,0,0,0,0]";
        attendanceStatusRepository.save(TestMakeObject.makeAttendanceStatus(user, vacationDates, absenceDates, weeklyData));

        AttendanceTime attendanceTime = new AttendanceTime(user);
        attendanceTimeRepository.save(attendanceTime);


        String basicWeeklyData = "[0,0,0,0,0]";
        String basicMonth = "";
        PeriodicData periodicDataOfNewUser = PeriodicData.builder()
                .userId(user.getId())
                .name(user.getName())
                .weeklyData(basicWeeklyData)
                .thisMonth(basicMonth)
                .previousMonth(basicMonth)
                .build();
        periodicDataRepository.save(periodicDataOfNewUser);
    }

    public void setUpOldUser() {
        // userId OB 2
        Long userId_2 = 2L;
        UserInfo userOb = userRepository.save(TestMakeObject.makeUserOb("졸업 유저", "2@gmail.com"));
        userRepository.updateId(userOb.getId(), userId_2);
        userOb.setId(userId_2);
    }
}
