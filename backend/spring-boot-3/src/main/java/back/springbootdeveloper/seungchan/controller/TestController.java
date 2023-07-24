package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.Suggestions;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import back.springbootdeveloper.seungchan.repository.SuggestionRepository;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserUtilRepository userUtilRepository;


    @GetMapping("/test")
    public String testController() {
        suggestionRepository.save(Suggestions.builder()
                .classification("test classification")
                .title("test Tle")
                .holidayPeriod("123 ~ 123")
                .build());
        return "Hello World";
    }

    @GetMapping("/user-test")
    public String userTestController() {
        userRepository.save(User.builder()
                .name("seungchabn")
                .phoneNum("010-2383-6578")
                .major("computer")
                .GPA("4.5")
                .address("수완동")
                .specialtySkill("잠자기")
                .hobby("잠")
                .MBTI("ENTP")
                .studentId("20161822")
                .birthDate("19960415")
                .advantages("열정")
                .disadvantage("단점")
                .selfIntroduction("안녕하세요")
                .photo("사진").build());
        return "Hello World";
    }

    @GetMapping("/userUtilTest")
    public String userUtilTestController() {
        userUtilRepository.save(UserUtill
                .builder()
                .userId(Long.valueOf(2))
                .name("박승찬")
                .cntVacation(5)
                .isNuriKing(true)
                .build());
        return "Hello World";
    }
}
