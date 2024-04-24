package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NoticesReqDto {

  @NotBlank(message = "{validation.notice.title.notblank}")
  private String noticeTitle;
  @NotBlank(message = "{validation.notice.content.notblank}")
  private String noticeContent;

  public Notice ofEntity() {
    return Notice.builder()
        .title(noticeTitle)
        .content(noticeContent)
        .build();
  }
}
