package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "notice")
public class Notice extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_id")
  private Long noticeId;

  @Column(name = "title", length = 255, nullable = false)
  private String title;

  @Column(name = "content", length = 1000, nullable = false)
  private String content;

  @Temporal(TemporalType.DATE)
  @Column(name = "notice_date", nullable = false)
  private LocalDate noticeDate;

  @Builder
  public Notice(final String title, final String content) {
    this.title = title;
    this.content = content;
  }

  public void updateTitle(final String title) {
    this.title = title;
  }

  public void updateContent(final String content) {
    this.content = content;
  }

  @PrePersist
  protected void onCreate() {
    LocalDateTime dateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
    this.noticeDate = zonedDateTime.toLocalDate();
  }

  public String getNoticeDate() {
    return String.valueOf(noticeDate);
  }


  /**
   * 주어진 공지와 현재 공지의 제목과 내용이 일치하는지 확인합니다.
   *
   * @param targetTitle   비교할 공지 제목
   * @param targetContent 비교할 공지 내용
   * @return 제목과 내용이 일치하면 true를 반환하고, 그렇지 않으면 false를 반환합니다. 비교할 공지가 null인 경우에는 false를 반환합니다.
   */

  public boolean is(final String targetTitle, final String targetContent) {
    if (this.title.equals(targetTitle) && this.content.equals(targetContent)) {
      return true;
    }
    return false;
  }
}
