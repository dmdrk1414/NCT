package back.springbootdeveloper.seungchan.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Suggestions {
    // classification
    // title
    // check
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "classification", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String classification;

    @Column(name = "title", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String title;

    @Column(name = "isCheck", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private boolean isCheck;

    @Builder
    public Suggestions(String classification, String title, boolean check) {
        this.classification = classification;
        this.title = title;
        this.isCheck = check;
    }
}
