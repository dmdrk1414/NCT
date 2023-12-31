package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.dto.request.RequestUserForm;
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
                .orElseThrow(() -> new UserNotExistException()); // 찾아서 없으면 예외처리.;
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
     * @param email        찾고자 하는 email
     * @param tempPassword 새로운 비밀번호
     */
    public void updateUserPassword(String email, String tempPassword) {
        String tempPasswordEncoder = new BCryptPasswordEncoder().encode(tempPassword);
        Integer OK = 1;

        if (!userRepository.updatePasswordByEmail(email, tempPasswordEncoder).equals(OK)) {
            throw new UserNotExistException();
        }
    }
}

