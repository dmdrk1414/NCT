package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "num_of_today_attendance")
public class NumOfTodayAttendence {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "check_num", length = 10, nullable = false, updatable = false)
    private String checkNum;

    @Column(name = "today", length = 20, nullable = false, updatable = false)
    private String day;

    @Builder
    public NumOfTodayAttendence(String checkNum, String day) {
        this.checkNum = checkNum;
        this.day = day;
    }
}
