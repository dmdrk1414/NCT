package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.*;
import back.springbootdeveloper.seungchan.repository.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@Hidden
public class TestController {
    private static Random random = new Random();
    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserUtilRepository userUtilRepository;
    @Autowired
    private AttendanceStatusRepository attendanceStatusRepository;
    @Autowired
    private NumOfTodayAttendenceRepository numOfTodayAttendenceRepository;

    // 랜덤한 MBTI 값 생성 메서드 예시
    private static String generateRandomMBTI(Random random) {
        String[] mbtiTypes = {"ISTJ", "ISFJ", "INFJ", "INTJ", "ISTP", "ISFP", "INFP", "INTP", "ESTP", "ESFP", "ENFP", "ENTP", "ESTJ", "ESFJ", "ENFJ", "ENTJ"};
        return mbtiTypes[random.nextInt(mbtiTypes.length)];
    }

    // 랜덤한 생년월일 값 생성 메서드 예시
    private static String generateRandomBirthDate(Random random) {
        int year = 1970 + random.nextInt(30); // 1970년부터 1999년 사이에서 랜덤한 년도 생성
        int month = 1 + random.nextInt(12); // 1월부터 12월 사이에서 랜덤한 월 생성
        int day = 1 + random.nextInt(28); // 1일부터 28일 사이에서 랜덤한 일 생성 (간단하게 28일로 가정)

        return String.format("%04d%02d%02d", year, month, day);
    }

    @GetMapping("/suggestion-test")
    public String testController() {
        List<String> classifications = Arrays.asList("건의", "비밀", "자유", "휴가");
        List<String> holidayPeriods = Arrays.asList(
                "2023-07-23 ~ 2023-07-30",
                "2023-08-10 ~ 2023-08-17",
                "2023-09-05 ~ 2023-09-12",
                "2023-10-01 ~ 2023-10-08"
        );
        List<String> titles = Arrays.asList(
                "첫 번째 건의입니다.",
                "비밀 제목입니다.",
                "자유롭게 작성한 제목입니다.",
                "휴가 신청합니다."
        );

        for (int i = 0; i < 4; i++) {
            suggestionRepository.save(Suggestions.builder()
                    .classification(classifications.get(i))
                    .title(titles.get(i))
                    .holidayPeriod(holidayPeriods.get(i))
                    .build());
        }
        return "Hello World";
    }

    @GetMapping("/user-test")
    public String userTestController() {
        String[] names = {"박승찬", "오성훈", "김주연", "허진범", "주형진"};
        String[] phoneNums = {"010-1111-1111", "010-2222-2222", "010-3333-3333", "010-4444-4444", "010-5555-5555"};
        String[] majors = {"computer", "math", "physics", "chemistry", "biology"};

        List<UserInfo> userList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < names.length; i++) {
            UserInfo user = UserInfo.builder()
                    .name(names[i])
                    .phoneNum(phoneNums[i])
                    .major(majors[i])
                    .gpa(String.format("%.1f", 3.0 + random.nextDouble() * 2.0))
                    .address("주소" + (i + 1))
                    .specialtySkill("특기" + (i + 1))
                    .hobby("취미" + (i + 1))
                    .mbti(generateRandomMBTI(random))
                    .studentId("학번" + (i + 1))
                    .birthDate(generateRandomBirthDate(random))
                    .advantages("장점" + (i + 1))
                    .disadvantage("단점" + (i + 1))
                    .selfIntroduction("자기소개" + (i + 1))
                    .photo("사진" + (i + 1))
                    .build();

            userList.add(user);
        }
        for (UserInfo user : userList) {
            userRepository.save(user);
        }
        return "Hello World";
    }

    @GetMapping("/userUtil-test")
    public String userUtilTestController() {
        List<UserInfo> userList = userRepository.findAll();
        for (UserInfo user : userList) {
            if ("박승찬".equals(user.getName())) {
                userUtilRepository.save(UserUtill
                        .builder()
                        .userId(Long.valueOf(user.getId()))
                        .name(user.getName())
                        .cntVacation(5)
                        .isNuriKing(true)
                        .build());
            } else {
                userUtilRepository.save(UserUtill
                        .builder()
                        .userId(Long.valueOf(user.getId()))
                        .name(user.getName())
                        .cntVacation(5)
                        .isNuriKing(false)
                        .build());
            }
        }
        return "Hello World";
    }

    @GetMapping("/attendance-test")
    public String attendanceStatus() {
        List<UserInfo> userList = userRepository.findAll();
        for (UserInfo user : userList) {
            attendanceStatusRepository.save(AttendanceStatus
                    .builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .vacationDates("07-02, 07-11, 07-13, 07-25")
                    .absenceDates("07-15, 07-16")
                    .weeklyData("[ 1, 1, 1, 0, -1 ]")
                    .build());
        }
        return "Hellow";
    }

    @GetMapping("/NumOfToday-test")
    public String numOfTodayAttendance() {
        numOfTodayAttendenceRepository.save(NumOfTodayAttendence.builder()
                .checkNum("1234")
                .day("2023-07-28")
                .build());
        return "Hellow";
    }
}
