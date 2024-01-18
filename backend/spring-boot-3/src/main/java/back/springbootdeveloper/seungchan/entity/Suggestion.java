package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "suggestions")
@AllArgsConstructor
public class Suggestion {
    // classification
    // title
    // check
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "classification", length = 5, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String classification;

    @Column(name = "title", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String title;

    @Column(name = "check_content", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private boolean isCheck;

    @Column(name = "holiday_period", length = 100, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String holidayPeriod;

    // 휴가 기간 포함 건의 사항
    @Builder
    public Suggestion(String classification, String title, String holidayPeriod) {
        this.classification = classification;
        this.title = title;
        this.isCheck = false;
        this.holidayPeriod = holidayPeriod;
    }

    // 전체적인 건의 사항
    @Builder
    public Suggestion(String classification, String title, boolean isCheck, String holidayPeriod) {
        this.classification = classification;
        this.title = title;
        this.isCheck = isCheck;
        this.holidayPeriod = holidayPeriod;
    }


    public Suggestion(Suggestion suggestions) {
        this.id = suggestions.getId();
        this.classification = suggestions.getClassification();
        this.title = suggestions.getTitle();
        this.isCheck = suggestions.isCheck();
        this.holidayPeriod = suggestions.getHolidayPeriod();
    }
}
