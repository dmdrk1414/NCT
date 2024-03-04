package back.springbootdeveloper.seungchan.util;

import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utill {

  public static boolean isEqualStr(String arg1, String arg2) {
    return arg1.equals(arg2);
  }

  public static List<String> arrFromStr(String str) {
    return List.of(str.split(", "));
  }

  /**
   * @param input "[1,1,1,1]" 의 문자열을 input
   * @return input의 문자열을 숫자만 추출하여 리스트로 만든다.
   */
  public static List<Integer> extractNumbers(String input) {
    List<Integer> integerList = new ArrayList<>();

    // 정규 표현식을 사용하여 숫자 추출
    Pattern pattern = Pattern.compile("-?\\d+");
    Matcher matcher = pattern.matcher(input);

    while (matcher.find()) {
      // 추출된 문자열을 Integer로 변환하여 리스트에 추가
      integerList.add(Integer.parseInt(matcher.group()));
    }

    return integerList;
  }

  /**
   * 입력된 문자열에서 출석 상태를 추출합니다.
   *
   * @param input 입력 문자열
   * @return 출석 상태 목록
   */
  public static List<ATTENDANCE_STATE> extractAttendanceState(String input) {
    List<ATTENDANCE_STATE> attendanceStates = new ArrayList<>();

    // 정규 표현식을 사용하여 숫자 추출
    Pattern pattern = Pattern.compile("-?\\d+"); // 정규 표현식: 하나 이상의 숫자를 찾음
    Matcher matcher = pattern.matcher(input); // 입력 문자열에서 패턴과 일치하는 부분을 찾기 위한 Matcher 생성

    while (matcher.find()) { // 일치하는 부분을 찾을 때까지 반복
      Integer number = Integer.parseInt(matcher.group()); // 일치하는 숫자 문자열을 정수로 변환하여 가져옴
      
      // 숫자가 각 출석 상태를 나타내는지 확인하고, 해당 상태를 목록에 추가
      if (ATTENDANCE_STATE.ATTENDANCE.is(number)) {
        attendanceStates.add(ATTENDANCE_STATE.ATTENDANCE);
      }
      if (ATTENDANCE_STATE.VACATION.is(number)) {
        attendanceStates.add(ATTENDANCE_STATE.VACATION);
      }
      if (ATTENDANCE_STATE.ABSENCE.is(number)) {
        attendanceStates.add(ATTENDANCE_STATE.ABSENCE);
      }
      if (ATTENDANCE_STATE.UNDECIDED.is(number)) {
        attendanceStates.add(ATTENDANCE_STATE.UNDECIDED);
      }
    }

    return attendanceStates; // 출석 상태 목록 반환
  }


  /**
   * @param target_1 비교대상_1
   * @param target_2 비교대상_2
   * @return 비교대상이 같으면 true, 다르면 false
   */
  public static boolean isSameInteger(int target_1, int target_2) {
    if (Integer.compare(target_1, target_2) == 0) {
      return true;
    }
    return false;
  }

  /**
   * printf ObjectList 출력 객체의 정보의 한줄 객체의 정보의 한줄 객체의 정보의 한줄
   */
  public static <T> void printObjectList(List<T> objectList) {
    System.out.println("printObjectList");
    for (int index = 0; index < objectList.size(); index++) {
      System.out.println(index + ": " + objectList.get(index));
    }
    System.out.println();
  }

  /**
   * 휴대폰 숫자가 번호 "01012341234"을 "010-1234-1234"으로 변경한다.
   *
   * @param number
   * @return
   */
  public static String formatPhoneNumber(String number) {
    if (number == null || number.length() != 11) {
      return "";
    }

    return number.substring(0, 3) + "-" + number.substring(3, 7) + "-" + number.substring(7, 11);
  }

  /**
   * 입력한 패스워드와 현제 유저의 패스워드가 같은지 확인한다
   *
   * @return
   */
  public static boolean isLoginMatches(String password, String hashPassword) {
    return new BCryptPasswordEncoder().matches(password, hashPassword);
  }
}
