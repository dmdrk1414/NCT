package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.CUSTOM_TYPE;
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
@Table(name = "custom_club_apply_information")
public class CustomClubApplyInformation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "custom_club_apply_information_id")
  private Long customClubApplyInformationId;

  @Column(name = "custom_content", length = 255, nullable = false)
  private String customContent;

  @Enumerated(EnumType.STRING)
  @Column(name = "custom_type", length = 30, nullable = false)
  private CUSTOM_TYPE customType;


  @Builder
  public CustomClubApplyInformation(final String customContent, final CUSTOM_TYPE customType) {
    this.customContent = customContent;
    this.customType = customType;
  }

  public void updateCustomContent(final String customContent) {
    this.customContent = customContent;
  }

  public void updateCustomType(final CUSTOM_TYPE customType) {
    this.customType = customType;
  }


}
