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
  private final UserService userService;

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


  /**
   * 모든 회원에게 휴가를 부여합니다.
   *
   * @param vacationToken 부여할 휴가의 양
   * @return 휴가 부여 작업이 성공했는지 여부를 나타내는 boolean 값입니다. 작업이 성공했으면 true를 반환합니다.
   */
  @Transactional
  public boolean giveVacatoin2AllMember(final Integer vacationToken) {
    List<UserUtill> userUtils = userUtilRepository.findAll();
    for (final UserUtill userUtil : userUtils) {
      UserInfo userInfo = userService.findUserById(userUtil.getUserId());

      if (!userInfo.isOb()) {
        int vacationNumAtNow = userUtil.getCntVacation();
        int resultVacationNum = vacationToken + vacationNumAtNow;

        userUtilRepository.updateCntVacationUserUtilData(userUtil.getUserId(), resultVacationNum);
      }
    }

    return true;
  }
}
