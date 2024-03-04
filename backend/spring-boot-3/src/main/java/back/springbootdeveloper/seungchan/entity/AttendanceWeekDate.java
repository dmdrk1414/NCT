package back.springbootdeveloper.seungchan.entity;


import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import jakarta.persistence.*;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "attendance_week_date")
public class AttendanceWeekDate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attendance_week_date_id")
  private Long attendanceWeekDateId;

  @Column(name = "user_id")
  private Long userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "monday", length = 15, nullable = false)
  private ATTENDANCE_STATE monday;

  @Enumerated(EnumType.STRING)
  @Column(name = "tuesday", length = 15, nullable = false)
  private ATTENDANCE_STATE tuesday;

  @Enumerated(EnumType.STRING)
  @Column(name = "wednesday", length = 15, nullable = false)
  private ATTENDANCE_STATE wednesday;

  @Enumerated(EnumType.STRING)
  @Column(name = "thursday", length = 15, nullable = false)
  private ATTENDANCE_STATE thursday;

  @Enumerated(EnumType.STRING)
  @Column(name = "friday", length = 15, nullable = false)
  private ATTENDANCE_STATE friday;

  @Temporal(TemporalType.DATE)
  @Column(name = "monday_date", nullable = false)
  private LocalDate mondayDate;

  @Temporal(TemporalType.DATE)
  @Column(name = "friday_date", nullable = false)
  private LocalDate fridayDate;

  @Column(name = "year_date")
  private String year_date;

  @Column(name = "month_date")
  private String month_date;


  @Builder
  public AttendanceWeekDate(ATTENDANCE_STATE monday, ATTENDANCE_STATE tuesday,
      ATTENDANCE_STATE wednesday, ATTENDANCE_STATE thursday, ATTENDANCE_STATE friday
  ) {
    this.monday = monday;
    this.tuesday = tuesday;
    this.wednesday = wednesday;
    this.thursday = thursday;
    this.friday = friday;
  }

  @PrePersist
  protected void onCreate() {
    ZonedDateTime now = ZonedDateTime.now(); // 현재 날짜와 시간을 가져옵니다.
    ZonedDateTime monday = now.with(DayOfWeek.MONDAY); // 현재 주의 월요일을 가져옵니다.
    ZonedDateTime sunday = now.with(DayOfWeek.SUNDAY); // 현재 주의 일요일을 가져옵니다.

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    this.mondayDate = LocalDate.parse(monday.format(formatter)); // "yyyy-MM-dd" 형식으로 포맷팅
    this.fridayDate = LocalDate.parse(sunday.format(formatter)); // "yyyy-MM-dd" 형식으로 포맷팅

    this.year_date = String.valueOf(now.getYear());
    this.month_date = String.valueOf(now.getMonthValue());

    this.monday = ATTENDANCE_STATE.UNDECIDED;
    this.tuesday = ATTENDANCE_STATE.UNDECIDED;
    this.wednesday = ATTENDANCE_STATE.UNDECIDED;
    this.thursday = ATTENDANCE_STATE.UNDECIDED;
    this.friday = ATTENDANCE_STATE.UNDECIDED;
  }

  /**
   * 오늘의 출석 상태를 업데이트합니다.
   */
  public void updateAttendanceAtToday() {
    ZonedDateTime now = ZonedDateTime.now();
    DayOfWeek currentDayOfWeek = now.getDayOfWeek();

    switch (currentDayOfWeek) {
      case MONDAY:
        this.monday = ATTENDANCE_STATE.ATTENDANCE;
        break;
      case TUESDAY:
        this.tuesday = ATTENDANCE_STATE.ATTENDANCE;
        break;
      case WEDNESDAY:
        this.wednesday = ATTENDANCE_STATE.ATTENDANCE;
        break;
      case THURSDAY:
        this.thursday = ATTENDANCE_STATE.ATTENDANCE;
        break;
      case FRIDAY:
        this.friday = ATTENDANCE_STATE.ATTENDANCE;
        break;
      default:
        break;
    }
  }

  /**
   * 오늘의 결석을 업데이트합니다.
   */
  public void updateAbsenceAtToday() {
    ZonedDateTime now = ZonedDateTime.now();
    DayOfWeek currentDayOfWeek = now.getDayOfWeek();

    switch (currentDayOfWeek) {
      case MONDAY:
        this.monday = ATTENDANCE_STATE.ABSENCE;
        break;
      case TUESDAY:
        this.tuesday = ATTENDANCE_STATE.ABSENCE;
        break;
      case WEDNESDAY:
        this.wednesday = ATTENDANCE_STATE.ABSENCE;
        break;
      case THURSDAY:
        this.thursday = ATTENDANCE_STATE.ABSENCE;
        break;
      case FRIDAY:
        this.friday = ATTENDANCE_STATE.ABSENCE;
        break;
      case SATURDAY:
      default:
        break;
    }
  }

  /**
   * 오늘의 휴가를 업데이트합니다.
   */
  public void updateVacationAtToday() {
    ZonedDateTime now = ZonedDateTime.now();
    DayOfWeek currentDayOfWeek = now.getDayOfWeek();

    switch (currentDayOfWeek) {
      case MONDAY:
        this.monday = ATTENDANCE_STATE.VACATION;
        break;
      case TUESDAY:
        this.tuesday = ATTENDANCE_STATE.VACATION;
        break;
      case WEDNESDAY:
        this.wednesday = ATTENDANCE_STATE.VACATION;
        break;
      case THURSDAY:
        this.thursday = ATTENDANCE_STATE.VACATION;
        break;
      case FRIDAY:
        this.friday = ATTENDANCE_STATE.VACATION;
        break;
      default:
        break;
    }
  }

  /**
   * 요일에 따른 출석 상태를 반환합니다.
   *
   * @param dayOfWeek 요일
   * @return 해당 요일의 출석 상태
   */
  public ATTENDANCE_STATE getAttendanceStateForDay(DayOfWeek dayOfWeek) {
    switch (dayOfWeek) {
      case MONDAY:
        return this.monday;
      case TUESDAY:
        return this.tuesday;
      case WEDNESDAY:
        return this.wednesday;
      case THURSDAY:
        return this.thursday;
      case FRIDAY:
        return this.friday;
      default:
        break;
    }
    return null;
  }

  /**
   * 현재 요일에 따라 출석 상태를 업데이트할 수 있는지 여부를 확인합니다.
   *
   * @return 출석 상태를 업데이트할 수 있는 경우 true, 그렇지 않으면 false를 반환합니다.
   */
  public Boolean isPossibleUpdateAttendanceState() {
    // 현재 시간을 기준으로 요일을 가져옵니다.
    ZonedDateTime now = ZonedDateTime.now();
    DayOfWeek currentDayOfWeek = now.getDayOfWeek();

    // 요일에 따라 처리를 수행합니다.
    switch (currentDayOfWeek) {
      case MONDAY:
        return this.monday.is(ATTENDANCE_STATE.UNDECIDED);
      case TUESDAY:
        return this.tuesday.is(ATTENDANCE_STATE.UNDECIDED);
      case WEDNESDAY:
        return this.wednesday.is(ATTENDANCE_STATE.UNDECIDED);
      case THURSDAY:
        return this.thursday.is(ATTENDANCE_STATE.UNDECIDED);
      case FRIDAY:
        return this.friday.is(ATTENDANCE_STATE.UNDECIDED);
    }
    // 기타 경우에는 업데이트가 불가능하므로 false를 반환합니다.
    return false;
  }

  public List<ATTENDANCE_STATE> getAllAttendanceWeekDates() {
    return List.of(
        this.monday, this.tuesday, this.wednesday, this.thursday, this.friday
    );
  }
}
