package back.springbootdeveloper.seungchan.controller.config;

import back.springbootdeveloper.seungchan.domain.Suggestions;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;

public class TestClassUtill {
    private static final String NAME_TEST = "seungchan141414@gmail.com";

    public static User makeUser() {
        User user = User.builder()
                .name(NAME_TEST)
                .phoneNum("010-2383-6578")
                .major("컴퓨터 공학과")
                .gpa("4.2")
                .address("수완동")
                .specialtySkill("특기")
                .hobby("달리기")
                .mbti("ENTP")
                .studentId("20161822")
                .birthDate("1996-04-15")
                .advantages("나의 장점은")
                .disadvantage("나의 단점")
                .selfIntroduction("자기소개")
                .photo("사진")
                .isOb(false)
                .build();
        user.setEmail("seungchan141414@gmail.com");
        user.setPassword(BCryptPasswordEncoderObject.getCryptPassword("1234"));
        return user;
    }

    public static UserUtill makeUserUtill(User testUser) {
        return UserUtill.builder()
                .name(NAME_TEST)
                .userId(testUser.getId())
                .cntVacation(5)
                .isNuriKing(true)
                .build();
    }

    public static Suggestions makeSuggestions() {
        final String classification = "건의";
        final String title = "Test 건의 제목";
        final String holiday_period = "2023-07-23 ~ 2023-07-30";

        return Suggestions.builder()
                .classification(classification)
                .title(title)
                .holidayPeriod(holiday_period)
                .build();
    }
}
