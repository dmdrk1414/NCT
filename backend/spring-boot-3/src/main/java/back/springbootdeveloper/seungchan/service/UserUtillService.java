package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.entity.VacationToken;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@ToString
public class UserUtillService {

  private final UserUtilRepository userUtilRepository;
  private final Integer BASE_VACATION_TOKEN = 6;

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

  public void subVacationCount(Long userId) {
    int vacationNumWantSub = 1;
    UserUtill userUtillByUserId = userUtilRepository.findByUserId(userId);
    int vacationNumAtNow = userUtillByUserId.getCntVacation();
    int resultVacationNum = vacationNumAtNow - vacationNumWantSub;

    userUtilRepository.updateCntVacationUserUtilData(userId, resultVacationNum);
  }

  public int cntVacation(Long userId) {
    UserUtill userUtill = userUtilRepository.findByUserId(userId);
    return userUtill.getCntVacation();
  }

  public void resetCntVacation() {
    userUtilRepository.resetCntVacation(BASE_VACATION_TOKEN);
  }

  public void saveNewUser(UserInfo newUser) {
    UserUtill newUserUtill = UserUtill.builder()
        .userId(newUser.getId())
        .name(newUser.getName())
        .cntVacation(BASE_VACATION_TOKEN)
        .isNuriKing(false)
        .isGeneralAffairs(false)
        .build();
    userUtilRepository.save(newUserUtill);
  }

  public UserUtill save(UserUtill userUtill) {
    return userUtilRepository.save(userUtill);
  }

  public void updateUserGrade(final Long id, final Boolean grade) {
    userUtilRepository.updateIsKingNuri(id, grade);
  }


  /**
   * 사용자 유틸리티 정보를 기반으로 휴가 토큰 목록을 반환합니다.
   *
   * @return 휴가 토큰 목록
   */
  public List<VacationToken> getVacationTokenList() {
    List<UserUtill> userUtils = userUtilRepository.findAll();

    return userUtils.stream()
        .map(VacationToken::new)
        .collect(Collectors.toList());
  }
}
