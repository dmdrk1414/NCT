package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "attendance_status")
public class AttendanceStatus {

  @Id // id 필드를 기본키로 지정
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "name", length = 10, nullable = false)
  private String name;

  @Column(name = "vacation_dates", length = 400, nullable = false)
  private String vacationDates;

  @Column(name = "absence_dates", length = 255, nullable = false)
  private String absenceDates;

  @Column(name = "weekly_data", length = 30, nullable = false)
  private String weeklyData = "[ 0, 0, 0, 0, 0]";

  @Builder
  public AttendanceStatus(Long userId, String name, String vacationDates, String absenceDates,
      String weeklyData) {
    this.userId = userId;
    this.name = name;
    this.vacationDates = vacationDates;
    this.absenceDates = absenceDates;
    this.weeklyData = weeklyData;
  }

  @Override
  public String toString() {
    return "AttendanceStatus{" +
        "id=" + id +
        ", userId=" + userId +
        ", name='" + name + '\'' +
        ", vacationDates='" + vacationDates + '\'' +
        ", absenceDates='" + absenceDates + '\'' +
        ", weeklyData='" + weeklyData + '\'' +
        '}';
  }
}
