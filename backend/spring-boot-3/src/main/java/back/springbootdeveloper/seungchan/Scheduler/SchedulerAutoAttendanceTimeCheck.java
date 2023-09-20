package back.springbootdeveloper.seungchan.Scheduler;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.service.AttendanceService;
import back.springbootdeveloper.seungchan.service.AttendanceTimeService;
import back.springbootdeveloper.seungchan.util.Utill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// @Scheduled(cron = "0/1 * * * * *") // 1초마다 테스트

@Component
public class SchedulerAutoAttendanceTimeCheck {
    private final String ATTENDANCE_TIME_09 = "09";
    private final String ATTENDANCE_TIME_10 = "10";
    private final String ATTENDANCE_TIME_11 = "11";
    private final String ATTENDANCE_TIME_12 = "12";
    private final String ATTENDANCE_TIME_13 = "13";
    private final String ATTENDANCE_TIME_14 = "14";
    private final String ATTENDANCE_TIME_15 = "15";
    private final String ATTENDANCE_TIME_16 = "16";
    private final String ATTENDANCE_TIME_17 = "17";
    private final String ATTENDANCE_TIME_18 = "18";
    private final String ATTENDANCE_TIME_19 = "19";
    private final String ATTENDANCE_TIME_20 = "20";
    private final String ATTENDANCE_TIME_21 = "21";
    private final String ATTENDANCE_TIME_22 = "22";
    private final String ATTENDANCE_TIME_23 = "23";
    private final String ATTENDANCE_TIME_24 = "24";

    @Autowired
    private AttendanceTimeService attendanceTimeService;

    @Autowired
    private AttendanceService attendanceService;

    private void printDateAtNow(String methodName) {
        System.out.println("실행된 함수 " + methodName + " : " + new Date());
    }

    /**
     * 매일 오전 9시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 9 * * *")
    public void autoCheckAttendanceTime09() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_09);
        printDateAtNow("autoCheckAttendanceTime09");
    }


    /**
     * 매일 오전 10시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 10 * * *")
    public void autoCheckAttendanceTime10() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_10);
        printDateAtNow("autoCheckAttendanceTime10");
    }

    /**
     * 매일 오전 11시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 11 * * *")
    public void autoCheckAttendanceTime11() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_11);
        printDateAtNow("autoCheckAttendanceTime11");
    }

    /**
     * 매일 오전 12시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 12 * * *")
    public void autoCheckAttendanceTime12() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_12);
        printDateAtNow("autoCheckAttendanceTime12");
    }

    /**
     * 매일 오후 1시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 13 * * *")
    public void autoCheckAttendanceTime13() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_13);
        printDateAtNow("autoCheckAttendanceTime13");
    }

    /**
     * 매일 오후 2시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 14 * * *")
    public void autoCheckAttendanceTime14() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_14);
        printDateAtNow("autoCheckAttendanceTime14");
    }

    /**
     * 매일 오후 3시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 15 * * *")
    public void autoCheckAttendanceTime15() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_15);
        printDateAtNow("autoCheckAttendanceTime15");
    }

    /**
     * 매일 오후 4시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 16 * * *")
    public void autoCheckAttendanceTime16() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_16);
        printDateAtNow("autoCheckAttendanceTime16");
    }

    /**
     * 매일 오후 5시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 17 * * *")
    public void autoCheckAttendanceTime17() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_17);
        printDateAtNow("autoCheckAttendanceTime17");
    }

    /**
     * 매일 오후 6시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 18 * * *")
    public void autoCheckAttendanceTime18() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_18);
        printDateAtNow("autoCheckAttendanceTime18");
    }

    /**
     * 매일 오후 7시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 19 * * *")
    public void autoCheckAttendanceTime19() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_19);
        printDateAtNow("autoCheckAttendanceTime19");
    }


    /**
     * 매일 오후 8시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 20 * * *")
    public void autoCheckAttendanceTime20() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_20);
        printDateAtNow("autoCheckAttendanceTime20");
    }


    /**
     * 매일 오후 9시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 21 * * *")
    public void autoCheckAttendanceTime21() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_21);
        printDateAtNow("autoCheckAttendanceTime21");
    }

    /**
     * 매일 오후 10시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 22 * * *")
    public void autoCheckAttendanceTime22() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_22);
        printDateAtNow("autoCheckAttendanceTime22");
    }

    /**
     * 매일 오후 11시 10분 출석에 대한 여부를 확인한다.
     */
    @Scheduled(cron = "0 10 23 * * *")
    public void autoCheckAttendanceTime23() {
        autoCheckAttendanceTime(ATTENDANCE_TIME_23);
        printDateAtNow("autoCheckAttendanceTime23");
    }

    /**
     * 출석을 원하는 시간에 출석을 하지않으면 결석처리한다.
     *
     * @param attendanceWantTimeOfUser 출석을 원하는 시간 ( "09" )
     */
    private void autoCheckAttendanceTime(String attendanceWantTimeOfUser) {
        List<AttendanceTime> attendanceTimeList = attendanceTimeService.findAll();
        List<AttendanceTime> attendanceTimeAboutTimeList = new ArrayList<>();
        String attendanceTimeEachUser = "";
        boolean isPassAttendanceAtToday = true;

        // 원하는 시간에 출석을 원하는 인원들 찾기
        for (AttendanceTime attendanceTime : attendanceTimeList) {
            attendanceTimeEachUser = attendanceTime.getAttendanceTime();
            if (sameAttendanceTime(attendanceWantTimeOfUser, attendanceTimeEachUser)) {
                attendanceTimeAboutTimeList.add(attendanceTime);
            }
        }

        // 원하는 시간에 출석을하지 않는 인원들은 결석처리를 한다.
        for (AttendanceTime attendanceTimeAbout09 : attendanceTimeAboutTimeList) {
            Long userId = attendanceTimeAbout09.getUserId();
            isPassAttendanceAtToday = attendanceService.isPassAttendanceAtToday(userId);
            if (!isPassAttendanceAtToday) {
                attendanceService.updateAbsenceVacationDate(userId);
            }
        }
    }

    private boolean sameAttendanceTime(String attendanceTime, String targetAttendanceTime) {
        return attendanceTime.equals(targetAttendanceTime);
    }
}
