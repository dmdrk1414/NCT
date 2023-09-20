package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    @Column(name = "attendance_time", length = 10, nullable = false, columnDefinition = "varchar(10) default '09'")
    private String attendanceTime;
    @Column(name = "exception_attendance", nullable = false)
    private boolean isExceptonAttendance;


    /**
     * user의 출석 시간 정보를 변경하기위한 생성자
     *
     * @param userInfo       UserInfo의 entity
     * @param attendanceTime 개인적으로 설정하고 싶은 출석의 시간을 설정
     */
    public AttendanceTime(UserInfo userInfo, String attendanceTime) {
        this.userId = userInfo.getId();
        this.name = userInfo.getName();
        this.attendanceTime = attendanceTime;
        this.isExceptonAttendance = false;
    }

    /**
     * user의 출석을 위한 시간을 기본적인 "09"으로 맞춘다.
     *
     * @param userInfo userInfo entity
     */
    public AttendanceTime(UserInfo userInfo) {
        this.userId = userInfo.getId();
        this.name = userInfo.getName();
        this.attendanceTime = BASE_ATTENDANCE_TIME;
        this.isExceptonAttendance = false;
    }
}
