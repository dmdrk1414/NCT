package back.springbootdeveloper.seungchan.Scheduler;

import back.springbootdeveloper.seungchan.domain.AttendanceStateList;
import back.springbootdeveloper.seungchan.entity.AttendanceWeekDate;
import back.springbootdeveloper.seungchan.entity.VacationToken;
import back.springbootdeveloper.seungchan.service.AttendanceService;
import back.springbootdeveloper.seungchan.service.AttendanceWeekDateService;
import back.springbootdeveloper.seungchan.service.NumOfTodayAttendenceService;
import back.springbootdeveloper.seungchan.service.PeriodicDataService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.service.VacationTokenService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
* * * * * *   //매초 실행
0 0 0/1 * * *   //1시간마다 실행
0 0 7 * * *   //매일 7시에 실행

1 2 3 4 5 6
* * * * * *
1 : 초 (0-59)
2 : 분 (0-59)
3 : 시간 (0-23)
4 : 일 (1-31)
5 : 월 (1-12)
6 : 요일 (0-7)  , 0과 7은 일요일, 1부터 월요일이고 6이 토요일
* : 모든조건(ALL)을 의미 합니다.
? : 설정 값 없을 때(어떠한 값이든 상관없습니다.) 다만 날짜와 요일에서만 사용가능 합니다.
-(하이픈) : 범위값을 지정할 때 사용 합니다.
,(콤마) : 여러값을 지정할 때 사용 합니다. ex) 0/2,0/5
/(슬러시) : 초기값과 증가치를 설정할 때 사용 합니다.
L : 마지막 - 지정할 수 있는 범위의 마지막 값 설정할때 사용 가능 합니다. 그리고 날짜와 요일에서만 사용 가능 합니다.
W : 가장 가까운 평일 찾는다 - 일 에서만 사용가능
# : 몇주 째인지 찾는다 - 요일 에서만 사용가능 합니다.
ex) 3#2 : 수요일#2째주 에 참

예제)
1) 매월 10일 오전 11시
cron = "0  1  1  10  *  *"
2) 매일 오후 2시 5분 0초
cron = "0  5  14  *  *  *"
3) 10분마다 도는 스케줄러 : 10분 0초, 20분 0초...
cron = "0  0/10  *  *  *"
4) 조건에서만 실행되는 스케줄러 : 10분 0초, 11분 0초 ~ 15분 0초까지 실행
cron = "0  10-15  *  *  *"
5)  5분 마다 실행 예) 00:05, 00:10. 00:15
cron = "0 0/5 * * * *"
6) 1시간 마다 실행 예) 01:00, 02:00, 03:00
cron = "0 0 0/1 * * *"
7) 매일 오후 18시마다 실행 예) 18:00
cron = "0 0 18 * * *"
8) 2018년도만 매일 오후 18시마다 실행 합니다. 예) 18:00
cron = "0 0 18 * * * 2018"
9) 매일 오후 18시00분-18시55분 사이에 5분 간격으로 실행 ex) 18:00, 18:05.....18:55
cron = "0 0/5 18 * * *"
10) 매일 오후 9시00분-9시55분, 18시00분-18시55분 사이에 5분 간격으로 실행 합니다.
cron = "0 0/5 9,18 * * *"
11) 매일 오후 9시00분-18시55분 사이에 5분 간격으로 실행 합니다.
cron = "0 0/5 9-18 * * *"
12) 매달 1일 00시에 실행 합니다.
cron = "0 0 0 1 * *"
13) 매년 3월내 월-금요일 10시 30분에만 실행 합니다.
cron = "0 30 10 ? 3 MON-FRI"
14) 매월 마지막날 저녁 10시에 실행 합니다.
cron = "0 0 10 L * ?"
https://itworldyo.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%8A%A4%EC%BC%80%EC%A4%84-%EC%84%A4%EC%A0%95-%EB%B2%95-Cron-%EC%A3%BC%EA%B8%B0%EC%84%A4%EC%A0%95
https://velog.io/@kekim20/Spring-boot-Scheduler%EC%99%80-cron%ED%91%9C%ED%98%84%EC%8B%9D

@Scheduled(cron = "0/1 * * * * *") // 매초마다.
 * */

@Component
public class SchedulerAutoWeekDataSave {

  @Autowired
  private AttendanceService attendanceService;
  @Autowired
  private AttendanceWeekDateService attendanceWeekDateService;
  @Autowired
  private UserUtillService userUtillService;
  @Autowired
  private VacationTokenService vacationTokenService;
  @Autowired
  private NumOfTodayAttendenceService numOfTodayAttendenceService;
  @Autowired
  private PeriodicDataService periodicDataService;

  private void printDateAtNow(String runMethodName) {
    System.out.println("실행된 함수 " + runMethodName + " : " + new Date());
  }

  /**
   * 주기적으로 실행되는 작업입니다. 매주 일요일 오후 11시 59분에 실행됩니다. 현재 주간 출석 상태를 반복해서 추가합니다.
   */
  @Scheduled(cron = "0 59 23 * * SUN") // 매주 일요일 오후23시 57분 run
  public void addAttendnaceWeekDateThisMonthRepeat() {
    List<AttendanceStateList> attendanceStateLists = attendanceService.getAllAttendanceWeekState();
    List<AttendanceWeekDate> attendanceWeekDates = attendanceStateLists.stream()
        .map(AttendanceWeekDate::new).collect(Collectors.toList());

    for (final AttendanceWeekDate attendanceWeekDate : attendanceWeekDates) {
      attendanceWeekDateService.save(attendanceWeekDate);
    }

    printDateAtNow("addAttendnaceWeekDateThisMonthRepeat");
  }

  @Scheduled(cron = "0 59 23 * * SUN") // 매주 일요일 오후23시 57분 run
  public void addVacationTokenDataThisMonthRepeat() {
    List<VacationToken> vacationTokens = userUtillService.getVacationTokenList();

    for (final VacationToken vacationToken : vacationTokens) {
      vacationTokenService.save(vacationToken);
    }

    printDateAtNow("addVacationTokenDataThisMonthRepeat");
  }
}
