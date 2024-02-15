package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_article_classification")
public class ClubArticleClassification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_article_classification_id")
    private Long clubArticleClassificationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "classification", length = 15, nullable = false)
    private CLUB_ARTICLE_CLASSIFICATION classification;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clubArticleClassification")
//    private List<ClubArticle> clubArticles;

    @Builder
    public ClubArticleClassification(CLUB_ARTICLE_CLASSIFICATION classification) {
        this.classification = classification;
    }

    public void updateClassification(CLUB_ARTICLE_CLASSIFICATION classification) {
        this.classification = classification;
    }

//    public List<ClubArticle> getClubArticles() {
//        return clubArticles;
//    }
//
//    public void addClubArticle(final ClubArticle clubArticle) {
//        this.clubArticles.add(clubArticle);
//        if (clubArticle.getClubArticleClassification() != this) { // 무한루프에 빠지지 않도록 체크
//            clubArticle.setClubArticleClassification(this);
//        }
//    }
}
