package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TeamArticleUpdateReqDto {
    @NotBlank(message = "{validation.team.article.title.notblank}")
    private String clubArticleUpdateTitle;

    @NotBlank(message = "{validation.team.article.content.notblank}")
    private String clubArticleUpdateCotent;
}
