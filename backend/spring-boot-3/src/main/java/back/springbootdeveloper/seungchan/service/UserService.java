package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.dto.request.RequestUserForm;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.filter.exception.judgment.UpdateFailedException;
import back.springbootdeveloper.seungchan.filter.exception.user.UserNotExistException;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
public class UserService {

  private final UserRepository userRepository;


  public UserInfo save(RequestUserForm userForm) {
    // controller에서 받은 데이터를
    // blogRepository에 의해 DB로 저장한다.
    return userRepository.save((userForm.toEntity()));
  }

  public UserInfo save(UserInfo userInfo) {
    // controller에서 받은 데이터를
    // blogRepository에 의해 DB로 저장한다.
    return userRepository.save(userInfo);
  }

  public UserInfo findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(UserNotExistException::new); // 찾아서 없으면 예외처리.;
  }

  public void updateUser(UserInfo userUpdate, Long userId) {
    userRepository.updateUser(userId, userUpdate);
  }

  public UserInfo findByEmail(String email) { // 유저 email 정보로 유저 객체를 찾기
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
  }

  public UserInfo saveNewUser(UserInfo newUser) {
    return userRepository.save(newUser);
  }

  public void existByEmailAndName(String email, String name) {
    Boolean exist = userRepository.existsByEmailAndName(email, name);

    if (!exist) {
      throw new UserNotExistException();
    }
  }

  /**
   * email로 찾은 유저의 비밀번호를 업데이트를 한다.
   *
   * @param email          찾고자 하는 유저의 email
   * @param updatePassword 새로운 비밀번호
   */
  public void updateUserPassword(String email, String updatePassword) {
    String tempPasswordEncoder = new BCryptPasswordEncoder().encode(updatePassword);
    Integer OK = 1;

    if (!userRepository.updatePasswordByEmail(email, tempPasswordEncoder).equals(OK)) {
      throw new UpdateFailedException();
    }
  }

  /**
   * 이메일  업데이트
   *
   * @param userId
   * @param updateEmail
   */
  public void updateEmail(Long userId, String updateEmail) {
    Integer OK = 1;

    if (!userRepository.updateEmailById(userId, updateEmail).equals(OK)) {
      throw new UpdateFailedException();
    }
  }

  /**
   * 유저의 이름을 통해 유저가 있는지 확인한다.
   *
   * @param name
   * @return
   */
  public Boolean existByNameAndPhoneNum(String name, String phoneNum) {
    if (userRepository.existsByNameAndPhoneNum(name, phoneNum)) {
      return true;
    }
    throw new UserNotExistException();
  }

  public UserInfo findByNameAndPhoneNum(String name, String phoneNum) {
    UserInfo user = userRepository.findByNameAndPhoneNum(name, phoneNum);

    if (user == null) {
      throw new UserNotExistException();
    }
    return user;
  }

  /**
   * 주어진 ID를 가진 사용자가 졸업했는지 여부를 확인합니다.
   *
   * @param id 확인할 사용자의 ID입니다.
   * @return 사용자가 졸업했으면 true, 그렇지 않으면 false를 반환합니다.
   * @throws EntityNotFoundException 주어진 ID와 일치하는 사용자를 찾을 수 없는 경우 발생합니다.
   */
  public Boolean idGraduationUser(final long id) {
    UserInfo userInfo = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return userInfo.isOb();
  }
}

