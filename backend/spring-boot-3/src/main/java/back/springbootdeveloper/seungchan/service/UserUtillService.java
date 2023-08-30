package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.domain.UserInfo;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@ToString
public class UserUtillService {
    private final UserUtilRepository userUtilRepository;

    public UserUtill findUserByUserId(Long userId) {
        return userUtilRepository.findByUserId(userId);
    }

    public boolean isNuriKing(Long userTempID) {
        UserUtill kingUser = userUtilRepository.findByUserId(userTempID);
        return kingUser.isNuriKing();
    }

    @Transactional
    public void addVacationConunt(Long userId, int vacationNumWantAdd) {
        UserUtill userUtillByUserId = userUtilRepository.findByUserId(userId);
        int vacationNumAtNow = userUtillByUserId.getCntVacation();
        int resultVacationNum = vacationNumWantAdd + vacationNumAtNow;

        userUtillByUserId.updateVacationNum(resultVacationNum);

        userUtilRepository.updateCntVacationUserUtilData(userId, resultVacationNum);
    }

    public void subVacationCount(Long userId, VacationRequest vacationRequest) {
        int vacationNumWantSub = vacationRequest.getCntUseOfVacation();
        UserUtill userUtillByUserId = userUtilRepository.findByUserId(userId);
        int vacationNumAtNow = userUtillByUserId.getCntVacation();
        int resultVacationNum = vacationNumAtNow - vacationNumWantSub;

        userUtillByUserId.updateVacationNum(resultVacationNum);

        userUtilRepository.updateCntVacationUserUtilData(userId, resultVacationNum);
    }

    public int cntVacation(Long userId) {
        UserUtill userUtill = userUtilRepository.findByUserId(userId);
        return userUtill.getCntVacation();
    }

    public void resetCntVacation() {
        int baseCntVacationNum = 5;
        userUtilRepository.resetCntVacation(baseCntVacationNum);
    }

    public void saveNewUser(UserInfo newUser) {
        int baseCntVacationNum = 5;
        UserUtill newUserUtill = UserUtill.builder()
                .userId(newUser.getId())
                .name(newUser.getName())
                .cntVacation(baseCntVacationNum)
                .isNuriKing(false)
                .isGeneralAffairs(false)
                .build();
        userUtilRepository.save(newUserUtill);
    }
}
