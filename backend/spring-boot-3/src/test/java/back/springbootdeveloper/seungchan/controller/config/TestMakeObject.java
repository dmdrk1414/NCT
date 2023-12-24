package back.springbootdeveloper.seungchan.controller.config;

import back.springbootdeveloper.seungchan.entity.*;

public class TestMakeObject {
    private static final String NAME_TEST = "현재실원_이름";
    private static final String TEST_PHONE_NUM = "010-1234-1234";
    private static final String TEST_MAJOR = "테스트 학과";
    private static final String TEST_GPA = "4.2";
    private static final String TEST_ADDRESS = "테스트 동";
    private static final String TEST_SPECIALTY_SKILL = "특기 테스트";
    private static final String TEST_HOBBY = "취미 테스트";
    private static final String TEST_MBTI = "ENTP";
    private static final String TEST_STUDENT_ID = "20002000";
    private static final String TEST_BIRTH_DATE = "2000-00-00";
    private static final String TEST_ADVANTAGES = "장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 장점 테스트 ";
    private static final String TEST_DIS_ADVANTAGES = "단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트단점 테스트";
    private static final String TEST_SELF_INTRODUCTION = "테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 테스트 자기소개 ";
    private static final String TEST_PHOTO = "테스트 사진";
    private static final Boolean TEST_NOT_OB = false;
    private static final String TEST_PASSWORD = "1234";
    private static final Boolean TEST_REGULAR_MEMBER = true;
    private static final Boolean TEST_OB = true;

    /**
     * 테스트에 필요한 유저을 생성한다.
     *
     * @param name
     * @param email
     * @return
     */
    public static UserInfo makeUser(String name, String email) {
        UserInfo user = UserInfo.builder()
                .name(name)
                .phoneNum(TEST_PHONE_NUM)
                .major(TEST_MAJOR)
                .gpa(TEST_GPA)
                .address(TEST_ADDRESS)
                .specialtySkill(TEST_SPECIALTY_SKILL)
                .hobby(TEST_HOBBY)
                .mbti(TEST_MBTI)
                .studentId(TEST_STUDENT_ID)
                .birthDate(TEST_BIRTH_DATE)
                .advantages(TEST_ADVANTAGES)
                .disadvantage(TEST_DIS_ADVANTAGES)
                .selfIntroduction(TEST_SELF_INTRODUCTION)
                .photo(TEST_PHOTO)
                .isOb(TEST_NOT_OB)
                .build();
        user.setEmail(email);
        user.setPassword(BCryptPasswordEncoderObject.getCryptPassword(TEST_PASSWORD));
        user.setRegularMember(TEST_REGULAR_MEMBER);

        return user;
    }

    public static UserInfo makeUserOb(String name, String email) {
        UserInfo user = UserInfo.builder()
                .name(name)
                .phoneNum(TEST_PHONE_NUM)
                .major(TEST_MAJOR)
                .gpa(TEST_GPA)
                .address(TEST_ADDRESS)
                .specialtySkill(TEST_SPECIALTY_SKILL)
                .hobby(TEST_HOBBY)
                .mbti(TEST_MBTI)
                .studentId(TEST_STUDENT_ID)
                .birthDate(TEST_BIRTH_DATE)
                .advantages(TEST_ADVANTAGES)
                .disadvantage(TEST_DIS_ADVANTAGES)
                .selfIntroduction(TEST_SELF_INTRODUCTION)
                .photo(TEST_PHOTO)
                .isOb(TEST_OB)
                .build();
        user.setEmail(email);
        user.setPassword(BCryptPasswordEncoderObject.getCryptPassword(TEST_PASSWORD));
        user.setRegularMember(TEST_REGULAR_MEMBER);

        return user;
    }

    public static TempUser makeNewUserOb(String email, String name) {
        TempUser user = TempUser.builder()
                .name(name)
                .phoneNum(TEST_PHONE_NUM)
                .major(TEST_MAJOR)
                .gpa(TEST_GPA)
                .address(TEST_ADDRESS)
                .specialtySkill(TEST_SPECIALTY_SKILL)
                .hobby(TEST_HOBBY)
                .mbti(TEST_MBTI)
                .studentId(TEST_STUDENT_ID)
                .birthDate(TEST_BIRTH_DATE)
                .advantages(TEST_ADVANTAGES)
                .disadvantage(TEST_DIS_ADVANTAGES)
                .selfIntroduction(TEST_SELF_INTRODUCTION)
                .photo(TEST_PHOTO)
                .isOb(TEST_NOT_OB)
                .email(email)
                .password(BCryptPasswordEncoderObject.getCryptPassword(TEST_PASSWORD))
                .build();

        return user;
    }

    public static UserUtill makeUserUtill(UserInfo testUser, int cntVacation, boolean isNuriKing) {
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

    public static AttendanceStatus makeAttendanceStatus(UserInfo user, String VacationDates, String absenceDataes, String weeklyData) {
        return AttendanceStatus
                .builder()
                .userId(user.getId())
                .name(user.getName())
                .vacationDates(VacationDates)
                .absenceDates(absenceDataes)
                .weeklyData(weeklyData)
                .build();
    }
}
