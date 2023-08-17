package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.dto.request.RequestUserForm;
import back.springbootdeveloper.seungchan.dto.request.UserLoginRequest;
import back.springbootdeveloper.seungchan.dto.response.UserLoginResponse;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
public class UserService {
    private final UserRepository userRepository;


    public User save(RequestUserForm userForm) {
        // controller에서 받은 데이터를
        // blogRepository에 의해 DB로 저장한다.
        return userRepository.save((userForm.toEntity()));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id)); // 찾아서 없으면 예외처리.;
    }

    public void updateUser(User userUpdate, Long userId) {
        userRepository.updateUser(userId, userUpdate);
    }

    public User findByEmail(String email) { // 유저 email 정보로 유저 객체를 찾기
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}

