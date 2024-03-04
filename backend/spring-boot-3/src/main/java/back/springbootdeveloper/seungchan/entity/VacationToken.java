package back.springbootdeveloper.seungchan.entity;


import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.domain.AttendanceStateList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vacation_token")
public class VacationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "vacation_token_id")
  private Long vacationTokenId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "vacation_token")
  private Integer vacation_token;

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
  public VacationToken(UserUtill userUtill) {
    this.userId = userUtill.getUserId();
    this.vacation_token = userUtill.getCntVacation();
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
  }
}
