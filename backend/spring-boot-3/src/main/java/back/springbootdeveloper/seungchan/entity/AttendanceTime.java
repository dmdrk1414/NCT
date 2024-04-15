package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(name = "attendance_time")
@Builder
public class AttendanceTime {

  static final String BASE_ATTENDANCE_TIME = "09";
  @Id // id 필드를 기본키로 지정
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
  @Column(name = "id", updatable = false)
  private Long id;
  @Column(name = "user_id", nullable = false)
  private Long userId;
  @Column(name = "name", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String name;
  @Column(name = "monday", length = 10, nullable = false, columnDefinition = "varchar(10) default '09'")
  private String monday;
  @Column(name = "tuesday", length = 10, nullable = false, columnDefinition = "varchar(10) default '09'")
  private String tuesday;
  @Column(name = "wednesday", length = 10, nullable = false, columnDefinition = "varchar(10) default '09'")
  private String wednesday;
  @Column(name = "thursday", length = 10, nullable = false, columnDefinition = "varchar(10) default '09'")
  private String thursday;
  @Column(name = "friday", length = 10, nullable = false, columnDefinition = "varchar(10) default '09'")
  private String friday;
  @Column(name = "exception_attendance", nullable = false)
  private boolean isExceptonAttendance;


  /**
   * user의 출석을 위한 시간을 기본적인 "09"으로 맞춘다.
   *
   * @param userInfo userInfo entity
   */
  public AttendanceTime(UserInfo userInfo) {
    this.userId = userInfo.getId();
    this.name = userInfo.getName();
    this.monday = BASE_ATTENDANCE_TIME;
    this.tuesday = BASE_ATTENDANCE_TIME;
    this.wednesday = BASE_ATTENDANCE_TIME;
    this.thursday = BASE_ATTENDANCE_TIME;
    this.friday = BASE_ATTENDANCE_TIME;
    this.isExceptonAttendance = false;
  }

  public List<String> getCustomTimes() {
    return List.of(monday, tuesday, wednesday, thursday, friday);
  }
}
