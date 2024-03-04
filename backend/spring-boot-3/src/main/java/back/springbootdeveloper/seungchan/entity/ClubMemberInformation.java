package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK;
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
@Table(name = "club_member_information")
public class ClubMemberInformation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_member_information_id")
  private Long clubMemberInformationId;

  @Column(name = "introduce", length = 1000, nullable = false)
  private String introduce;

  @Enumerated(EnumType.STRING)
  @Column(name = "favorite_check", length = 15, nullable = false)
  private FAVORITE_CHECK favoriteCheck = FAVORITE_CHECK.UNCHECK;

  @Builder
  public ClubMemberInformation(String introduce) {
    this.introduce = introduce;
  }

  /**
   * 소개를 업데이트하는 메서드입니다.
   *
   * @param introduce 새로운 소개
   */
  public void updateIntroduce(String introduce) {
    this.introduce = introduce;
  }

  /**
   * 즐겨찾기 확인을 업데이트하는 메서드입니다.
   *
   * @param favoriteCheck 새로운 즐겨찾기 확인
   */
  public void updateFavoriteCheck(FAVORITE_CHECK favoriteCheck) {
    this.favoriteCheck = favoriteCheck;
  }

}
