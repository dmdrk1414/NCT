package back.springbootdeveloper.seungchan.entity;


import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.domain.AttendanceStateList;
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
  public AttendanceWeekDate(AttendanceStateList attendanceStateList) {
    this.userId = attendanceStateList.getUserId();
    this.monday = attendanceStateList.getAttendanceStates().get(0);
    this.tuesday = attendanceStateList.getAttendanceStates().get(1);
    this.wednesday = attendanceStateList.getAttendanceStates().get(2);
    this.thursday = attendanceStateList.getAttendanceStates().get(3);
    this.friday = attendanceStateList.getAttendanceStates().get(4);
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


  public List<ATTENDANCE_STATE> getAllAttendanceWeekDates() {
    return List.of(
        this.monday, this.tuesday, this.wednesday, this.thursday, this.friday
    );
  }
}
