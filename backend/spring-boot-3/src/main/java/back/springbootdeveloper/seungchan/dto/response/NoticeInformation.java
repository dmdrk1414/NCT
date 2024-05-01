package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NoticeInformation {

  private Long noticeId;
  private String title;
  private String content;
  private String date;

  @Builder
  public NoticeInformation(final Notice notice) {
    this.noticeId = notice.getNoticeId();
    this.title = notice.getTitle();
    this.content = notice.getContent();
    this.date = notice.getNoticeDate();
  }
}
