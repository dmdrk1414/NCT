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

  @PrePersist
  protected void onCreate() {
    LocalDateTime dateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
    this.noticeDate = zonedDateTime.toLocalDate();
  }

  public String getNoticeDate() {
    return String.valueOf(noticeDate);
  }
}
