package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.TempUser;
import back.springbootdeveloper.seungchan.dto.request.TempUserFormReqDto;
import back.springbootdeveloper.seungchan.dto.response.NewUsersResDto;
import back.springbootdeveloper.seungchan.filter.exception.user.NewUserRegistrationException;
import back.springbootdeveloper.seungchan.repository.TempUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TempUserService {

  private final TempUserRepository tempUserRepository;

  public void save(TempUserFormReqDto requestUserForm) {
    TempUser newTempUser = requestUserForm.toEntity();
    TempUser tempUser = tempUserRepository.save(newTempUser);

    if (tempUser == null) {
      throw new NewUserRegistrationException();
    }
  }

  /**
   * 임시 유저를 찾아서 response으로 변환하여 반환
   *
   * @return
   */
  public List<NewUsersResDto> findAllNewUsers() {
    List<TempUser> newUsersResponses = tempUserRepository.findAll();

    return newUsersResponses.stream()
        .map(tempUser -> NewUsersResDto.builder()
            .id(tempUser.getId())
            .email(tempUser.getEmail())
            .name(tempUser.getName())
            .applicationDate(tempUser.getApplicationDate())
            .build())
        .collect(Collectors.toList());
  }

  public TempUser findNewUsers(long id) {
    return tempUserRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected new user"));
  }

  public List<TempUser> findAll() {
    return tempUserRepository.findAll();
  }

  public TempUser removeTempUserByEmail(String emailOfNewUser) {
    TempUser tempUser = tempUserRepository.findByEmail(emailOfNewUser)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected new user"));
    tempUserRepository.deleteByEmail(emailOfNewUser);

    return tempUser;
  }

  public TempUser removeTempUserById(Long idOfNewUser) {
    TempUser tempUser = tempUserRepository.findById(idOfNewUser)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected new user"));
    tempUserRepository.deleteById(idOfNewUser);

    return tempUser;
  }

  public Boolean exist(String email) {
    return tempUserRepository.existsByEmail(email);
  }

  /**
   * 새로운 임시 회원의 정보를 저장한다.가
   */
  public TempUser save(TempUser tempUser) {
    return tempUserRepository.save(tempUser);
  }
}
