package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.AttendanceStateConstant;
import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.domain.AttendanceStateList;
import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.dto.response.VacationsResponce;
import back.springbootdeveloper.seungchan.repository.AttendanceStatusRepository;
import back.springbootdeveloper.seungchan.util.DayUtill;
import back.springbootdeveloper.seungchan.util.Utill;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class AttendanceService {

  private final int attendanceOK = 1;
  private final int ABSENCE_TODAY = -1;
  private final int VACATION_TODAY = 2;

  @Autowired
  private AttendanceStatusRepository attendanceStatusRepository;

  private static boolean isContains(String vacationDates, String dateRequest) {
    return vacationDates.contains(dateRequest);
  }

  @Transactional
  public void UpdateweeklyData(int indexDay, Long userId) {
    // [ 1, 1, 1, 0, -1 ]
    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
    String weeklyData = attendanceStatus.getWeeklyData();

    String updateWeeklyData = updateOfWeeklyData(weeklyData, indexDay);

    attendanceStatusRepository.updateWeeklyDataByUserId(userId, updateWeeklyData);
  }

  /**
   * [ 1, 1, 1, 0, -1 ]을 indexDay = 5을 입력하면 [ 1, 1, 1, 0, 1 ] 으로 변환해준다.
   *
   * @param weeklyData "[ 1, 1, 1, 0, -1 ]"의 문자열
   * @param indexDay   변경을 원하는 index MONDAY = 0
   * @return
   */
  private String updateOfWeeklyData(String weeklyData, int indexDay) {
    // "[ 1, 1, 1, 0, -1 ]"
    JSONArray jsonArray = new JSONArray(weeklyData);
    int[] intArray = new int[jsonArray.length()];

    for (int i = 0; i < jsonArray.length(); i++) {
      intArray[i] = jsonArray.getInt(i);
    }

    intArray[indexDay] = attendanceOK;

    // [ 1, 1, 1, 0, 1 ]
    JSONArray resultJsonArray = arrayToJSONArray(intArray);
    return resultJsonArray.toString();
  }

  public JSONArray arrayToJSONArray(int[] arr) {
    JSONArray jsonArray = new JSONArray();
    for (int i = 0; i < arr.length; i++) {
      jsonArray.put(arr[i]);
    }
    return jsonArray;
  }

  /**
   * Attendance_status의 테이블의 vacationDate을 업데이트하는 함수
   *
   * @param userId
   * @param vacationRequest
   */
  public void updateVacationDate(Long userId, VacationRequest vacationRequest) {
    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
    String vacationDates = attendanceStatus.getVacationDates();
    String[] vacationDateOfRequest = vacationRequest.getPreVacationDate();

    String resultStr = attendanceStatus.getVacationDates();
    for (String dateRequest : vacationDateOfRequest) {
      if (!isContains(vacationDates, dateRequest)) {
        resultStr = resultStr + ", " + dateRequest;
      }
    }
    attendanceStatusRepository.updateVacationDatesByUserId(userId, resultStr);
  }

  public VacationsResponce findVacations(Long userId) {
    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
    String absencesStr = attendanceStatus.getAbsenceDates();
    String vacationDateStr = attendanceStatus.getVacationDates();

    List<String> absences = Utill.arrFromStr(absencesStr);
    List<String> beforeVacationDate = new ArrayList<>();
    List<String> preVacationDate = new ArrayList<>();
    getBeforAndPreVacationDate(vacationDateStr, beforeVacationDate, preVacationDate);

    return new VacationsResponce(absences, beforeVacationDate, preVacationDate);
  }

  private void getBeforAndPreVacationDate(String vacationDateStr, List<String> beforeVacationDate,
      List<String> preVacationDate) {
    List<String> vacationList = Utill.arrFromStr(vacationDateStr);
    String todayStr = getTodays();
    LocalDate todayDate = LocalDate.parse(todayStr);

    for (String dateString : vacationList) {
      LocalDate date = LocalDate.parse(dateString);
      if (date.isBefore(todayDate) || date.isEqual(todayDate)) {
        beforeVacationDate.add(dateString);
      } else if (date.isAfter(todayDate)) {
        preVacationDate.add(dateString);
      }
    }
  }

  private String getTodays() {
    // 오늘 날짜를 가져오기
    LocalDate today = LocalDate.now();
    // 년도, 월, 일 추출
    int year = today.getYear();
    int month = today.getMonthValue();
    int day = today.getDayOfMonth();
    return String.format("%04d-%02d-%02d", year, month, day);
  }

  public void resetWeeklyData() {
    String resetWeeklyData = "[0,0,0,0,0]";
    attendanceStatusRepository.resetWeeklyData(resetWeeklyData);
  }

  public void resetAbsenceDates() {
    String resetAbsenceData = "";
    attendanceStatusRepository.resetAbsenceDate(resetAbsenceData);
  }

  public void resetVacationDates() {
    String resetVacationDate = "";
    attendanceStatusRepository.resetVacationDate(resetVacationDate);
  }

  public void saveNewUser(UserInfo newUser) {
    String basicDate = "";
    String basicWeeklyData = "[0,0,0,0,0]";
    AttendanceStatus attendanceStatusOfNewUser = AttendanceStatus.builder()
        .userId(newUser.getId())
        .name(newUser.getName())
        .vacationDates(basicDate)
        .absenceDates(basicDate)
        .weeklyData(basicWeeklyData)
        .build();

    attendanceStatusRepository.save(attendanceStatusOfNewUser);
  }

  public List<AttendanceStatus> findAll() {
    return attendanceStatusRepository.findAll();
  }

  public boolean isPassAttendanceAtToday(Long userIdOfSearch) {
    boolean isPassAttendance = true;
    int COMPLETE_ATTENDANCE = 1;
    int attendanceStatusNumOneAndZero = 0;
    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userIdOfSearch);
    String weeklyDataStr = attendanceStatus.getWeeklyData();
    List<Integer> weeklyDataList = Utill.extractNumbers(weeklyDataStr);
    int indexOfWeekDay = DayUtill.getIndexOfWeekDay();
    int NUM_SATURDAY_SUNDAY = 5;

    if (indexOfWeekDay == NUM_SATURDAY_SUNDAY) {
      return false;
    } else {
      // 출석했으면 1, 출석안했으면 0
      attendanceStatusNumOneAndZero = weeklyDataList.get(indexOfWeekDay);
    }

    isPassAttendance = Utill.isSameInteger(COMPLETE_ATTENDANCE, attendanceStatusNumOneAndZero);
    return isPassAttendance;
  }

  public boolean isPassVacationAtToday(Long userIdOfSearch) {
    boolean isPassVacation = true;
    int COMPLETE_ATTENDANCE = 1;
    int attendanceStatusc = 0;
    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userIdOfSearch);
    String weeklyDataStr = attendanceStatus.getWeeklyData();
    List<Integer> weeklyDataList = Utill.extractNumbers(weeklyDataStr);
    int indexOfWeekDay = DayUtill.getIndexOfWeekDay();
    int NUM_SATURDAY_SUNDAY = 5;

    if (indexOfWeekDay == NUM_SATURDAY_SUNDAY) {
      return false;
    } else {
      // 방학여부
      attendanceStatusc = weeklyDataList.get(indexOfWeekDay);
    }

    isPassVacation = Utill.isSameInteger(AttendanceStateConstant.VACATION.getState(),
        attendanceStatusc);
    return isPassVacation;
  }

  public void updateAbsenceVacationDate(Long userId) {
    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
    String weeklyData = attendanceStatus.getWeeklyData();
    int indexDay = DayUtill.getIndexDayOfWeek();

    // 문자열 json으로 변경 "[ 0, 0, 0, 0, 0 ]"
    JSONArray jsonArray = new JSONArray(weeklyData);
    int[] intArray = new int[jsonArray.length()];

    for (int i = 0; i < jsonArray.length(); i++) {
      intArray[i] = jsonArray.getInt(i);
    }

    intArray[indexDay] = ABSENCE_TODAY;

    // [ 1, 1, 1, 0, -1 ]
    JSONArray resultJsonArray = arrayToJSONArray(intArray);
    String updateWeeklyData = resultJsonArray.toString();

    attendanceStatusRepository.updateWeeklyDataByUserId(userId, updateWeeklyData);
  }

  /**
   * userId을 이용하여 원하는 유저의 휴가를 사용하는 것으로 표시 [... "0" ] => [... "2"] 으로 변경
   *
   * @param userId
   */
  public void updateVacationDate2PassVacation(Long userId) {
    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
    String weeklyData = attendanceStatus.getWeeklyData();
    int indexDay = DayUtill.getIndexDayOfWeek();

    // 문자열 json으로 변경 "[ 0, 0, 0, 0, 0 ]"
    JSONArray jsonArray = new JSONArray(weeklyData);
    int[] intArray = new int[jsonArray.length()];

    for (int i = 0; i < jsonArray.length(); i++) {
      intArray[i] = jsonArray.getInt(i);
    }

    // indexDay의 index에 3으로 변경 (휴가 표시)
    intArray[indexDay] = VACATION_TODAY;

    // [ 0, 0, 0, 0, 2 ]
    JSONArray resultJsonArray = arrayToJSONArray(intArray);
    String updateWeeklyData = resultJsonArray.toString();

    attendanceStatusRepository.updateWeeklyDataByUserId(userId, updateWeeklyData);
  }

  /**
   * 해당 유저을 금일 출석 상태로 변경한다.
   *
   * @param userId
   */
  public void updateAttendanceToday(Long userId) {
    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
    String weeklyData = attendanceStatus.getWeeklyData();
    int indexDay = DayUtill.getIndexDayOfWeek();

    // 문자열 json으로 변경 "[ 0, 0, 0, 0, 0 ]"
    JSONArray jsonArray = new JSONArray(weeklyData);
    int[] intArray = new int[jsonArray.length()];

    for (int i = 0; i < jsonArray.length(); i++) {
      intArray[i] = jsonArray.getInt(i);
    }

    // indexDay의 index에 출석으로 변경
    intArray[indexDay] = AttendanceStateConstant.ATTENDANCE.getState();

    // [ 0, 0, 0, 0, 2 ]
    JSONArray resultJsonArray = arrayToJSONArray(intArray);
    String updateWeeklyData = resultJsonArray.toString();

    attendanceStatusRepository.updateWeeklyDataByUserId(userId, updateWeeklyData);
  }

  /**
   * 출석이 가능한 상태인지 확인한다.
   *
   * @param userId
   */
  public Boolean available(Long userId) {
    Integer unDecided = AttendanceStateConstant.UN_DECIDED.getState();
    String weeklyDataString = "";
    List<Integer> weeklyDatas = null;
    Integer attendanceTodayState = 0;

    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
    weeklyDataString = attendanceStatus.getWeeklyData();
    weeklyDatas = Utill.extractNumbers(weeklyDataString);
    attendanceTodayState = weeklyDatas.get(DayUtill.getIndexDayOfWeek());

    if (attendanceTodayState.equals(unDecided)) {
      return true;
    }
    return false;
  }

  /**
   * 휴가 신청 가능 한지 알수있다.
   *
   * @param userId
   * @return
   */
  public boolean availableApplyVacation(Long userId) {
    Integer unDecided = AttendanceStateConstant.UN_DECIDED.getState();
    String weeklyDataString = "";
    List<Integer> weeklyDatas = null;
    Integer attendanceTodayState = 0;

    AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
    weeklyDataString = attendanceStatus.getWeeklyData();
    weeklyDatas = Utill.extractNumbers(weeklyDataString);
    attendanceTodayState = weeklyDatas.get(DayUtill.getIndexDayOfWeek());

    if (attendanceTodayState.equals(unDecided)) {
      return true;
    }
    return false;
  }

  public AttendanceStatus save(AttendanceStatus attendanceStatus) {
    return attendanceStatusRepository.save(attendanceStatus);
  }

  public AttendanceStatus findById(Long userId) {
    return attendanceStatusRepository.findByUserId(userId);
  }

  /**
   * 모든 주간 출석 상태를 가져옵니다.
   *
   * @return 주간 출석 상태 목록
   */
  public List<AttendanceStateList> getAllAttendanceWeekState() {
    List<AttendanceStatus> attendanceStatuses = attendanceStatusRepository.findAll(); // 모든 출석 상태 조회
    List<AttendanceStateList> attendanceStateLists = new ArrayList<>(); // 주간 출석 상태 목록 초기화

    for (final AttendanceStatus attendanceStatus : attendanceStatuses) { // 각 출석 상태에 대해 반복
      String weeklyData = attendanceStatus.getWeeklyData(); // 주간 데이터 가져오기
      List<ATTENDANCE_STATE> attendanceStates = Utill.extractAttendanceState(
          weeklyData); // 주간 데이터에서 출석 상태 추출

      attendanceStateLists.add(AttendanceStateList.builder()
          .attendanceStates(attendanceStates)
          .userId(attendanceStatus.getUserId())
          .build()); // 주간 출석 상태 목록에 추가
    }

    return attendanceStateLists; // 주간 출석 상태 목록 반환
  }
}
