package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.CUSTOM_TYPE;
import back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_member_custom_information")
public class ClubMemberCustomInformation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_member_custom_information_id")
  private Long clubMemberCustomInformationId;

  @Column(name = "custom_content", length = 255, nullable = false)
  private String customContent;

  @Enumerated(EnumType.STRING)
  @Column(name = "custom_type", length = 30, nullable = false)
  private CUSTOM_TYPE customType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "club_member_information_id")
  private ClubMemberInformation clubMemberInformation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "club_control_id")
  private ClubControl clubControl;

  @Builder
  public ClubMemberCustomInformation(final String customContent, final CUSTOM_TYPE customType) {
    this.customContent = customContent;
    this.customType = customType;
  }

  public void updateCustomContent(final String customContent) {
    this.customContent = customContent;
  }

  public void updateCustomType(final CUSTOM_TYPE customType) {
    this.customType = customType;
  }

  /**
   * 클럽 멤버의 정보를 설정합니다.
   *
   * @param clubMemberInformation 설정할 클럽 멤버의 정보
   */
  public void setClubMemberInformation(final ClubMemberInformation clubMemberInformation) {
    this.clubMemberInformation = clubMemberInformation;

    // 무한루프에 빠지지 않도록 체크
    if (!clubMemberInformation.getClubMemberCustomInformations().contains(this)) {
      clubMemberInformation.getClubMemberCustomInformations().add(this);
    }
  }

  /**
   * 클럽 컨트롤을 설정합니다.
   *
   * @param clubControl 설정할 클럽 컨트롤
   */
  public void setClubControl(final ClubControl clubControl) {
    this.clubControl = clubControl;

    // 무한루프에 빠지지 않도록 체크
    if (!clubControl.getClubMemberCustomInformations().contains(this)) {
      clubControl.getClubMemberCustomInformations().add(this);
    }
  }
}
