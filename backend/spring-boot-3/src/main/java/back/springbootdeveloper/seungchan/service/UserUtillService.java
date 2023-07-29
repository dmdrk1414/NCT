package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
public class UserUtillService {
    private final UserUtilRepository userUtilRepository;

    public UserUtill findUserByUserId(Long userId) {
        return userUtilRepository.findByUserId(userId);
    }

    public boolean isNuriKing(Long userTempID) {
        UserUtill kingUser = userUtilRepository.findByUserId(userTempID);
        return kingUser.isNuriKing();
    }
}
