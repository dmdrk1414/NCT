package back.springbootdeveloper.seungchan.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "attendance_state")
public class AttendanceSate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_state_id")
    private Long attendanceStateId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attendance_week_date_id")
    private AttendanceWeekDate attendanceWeekDate;

    public void setAttendanceWeekDate(final AttendanceWeekDate attendanceWeekDate) {
        this.attendanceWeekDate = attendanceWeekDate;

        if (attendanceWeekDate.getAttendanceSate() != this) { // null 체크 추가
            attendanceWeekDate.setAttendanceSate(this);
        }
    }
}
