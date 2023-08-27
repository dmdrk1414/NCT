package back.springbootdeveloper.seungchan.controller.config;

import back.springbootdeveloper.seungchan.domain.*;

public class TestClassUtill {
    private static final String NAME_TEST = "현재실원_이름";

    public static User makeUser(String name, String email) {
        User user = User.builder()
                .name(name)
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
        user.setEmail(email);
        user.setPassword(BCryptPasswordEncoderObject.getCryptPassword("1234"));
        user.setRegularMember(true);
        return user;
    }

    public static User makeUserOb(String name, String email) {
        User user = User.builder()
                .name(name)
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
                .isOb(true)
                .build();
        user.setEmail(email);
        user.setPassword(BCryptPasswordEncoderObject.getCryptPassword("1234"));
        user.setRegularMember(true);

        return user;
    }

    public static TempUser makeNewUserOb(String email, String password) {
        TempUser user = TempUser.builder()
                .name("새로 희망 유저")
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
                .email(email)
                .password(password)
                .build();

        return user;
    }

    public static UserUtill makeUserUtill(User testUser, int cntVacation, boolean isNuriKing) {
        return UserUtill.builder()
                .name(testUser.getName())
                .userId(testUser.getId())
                .cntVacation(cntVacation)
                .isNuriKing(isNuriKing)
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

    public static AttendanceStatus makeAttendanceStatus(User user, String VacationDates, String absenceDataes, String weeklyData) {
        return AttendanceStatus
                .builder()
                .userId(user.getId())
                .name(user.getName())
                .vacationDates("2023-07-02, 2023-07-11, 2023-07-13, 2023-07-25, 2023-07-31")
                .absenceDates("2023-07-15, 2023-07-16")
                .weeklyData("[1,1,1,0,1]")
                .build();
    }
}
