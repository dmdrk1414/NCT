package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.domain.ObUser;
import back.springbootdeveloper.seungchan.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ObUserOfMainResponse {
    private List<ObUser> obUserList;
    private boolean isNuriKing;

    @Builder
    public ObUserOfMainResponse(List<ObUser> obUserList, boolean isNuriKing) {
        this.obUserList = obUserList;
        this.isNuriKing = isNuriKing;
    }
}
