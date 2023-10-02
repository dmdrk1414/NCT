package back.springbootdeveloper.seungchan.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

public class DayUtill {

    /**
     * @return 오늘이 몇 요일인지 확인 index로 준다.
     * 토요일과 일요일은 5으로 처리, 그 외 요일은 0부터 4까지의 값을 반환
     */
    public static int getIndexOfWeekDay() {
        // 현재 날짜와 시간 가져오기
        Date currentDate = new Date();

        // Calendar 객체 생성 및 현재 날짜 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // 현재 요일 얻기 (1: 일요일, 2: 월요일, ..., 7: 토요일)
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        int NUM_SATURDAY_SUNDAY = 5;
        // 토요일 (7)과 일요일 (1)은 5으로 처리, 그 외 요일은 0부터 4까지의 값을 반환
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return NUM_SATURDAY_SUNDAY;
        } else {
            return dayOfWeek - Calendar.MONDAY;
        }
    }

    public static DayOfWeek getDayOfWeekAtNow(String dayStr) {
        int year = DayUtill.getYearFromDayStr(dayStr);
        int month = DayUtill.getMonthFromDayStr(dayStr);
        int dayOfMonth = DayUtill.getDayFromDayStr(dayStr);

        // LocalDate 객체를 생성합니다.
        LocalDate date = LocalDate.of(year, month, dayOfMonth);

        // 요일을 얻습니다.
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            return dayOfWeek;
        }
        throw new IllegalArgumentException("토요일 일요일은 안됩니다.");
        // 1 MONDAY : 월요일 (DayOfWeek.MONDAY)
        // 2 TUESDAY : 화요일 (DayOfWeek.TUESDAY)
        // 3 WEDNESDAY : 수요일 (DayOfWeek.WEDNESDAY)
        // 4 THURSDAY : 목요일 (DayOfWeek.THURSDAY)
        // 5 FRIDAY : 금요일 (DayOfWeek.FRIDAY)
        // 5 SATURDAY : 토요일 (DayOfWeek.SATURDAY)
        // 6 SUNDAY : 일요일 (DayOfWeek.SUNDAY)
    }

    public static int getYearFromDayStr(String day) {
        String year = day.split("-")[0];
        return Integer.parseInt(year);
    }

    public static String getYear() {
        Year currentYear = Year.now();
        return String.valueOf(currentYear.getValue());
    }


    public static int getMonthFromDayStr(String day) {
        String month = day.split("-")[1];
        return Integer.parseInt(month);
    }

    public static int getDayFromDayStr(String day) {
        String dayOfMonth = day.split("-")[2];
        return Integer.parseInt(dayOfMonth);
    }


    /**
     * @return map ( 현재시점에서의 몇월인지, 현재시점에서 몇주차인지 )
     * map ("month", currentMonth)
     * map ("currentWeekNumber, currentWeekNumber)
     */
    public static Map<String, Integer> getMonthAndWeekNumberInMonthMap() {
        Map<String, Integer> map = new HashMap<>();
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        // WeekFields를 사용하여 주차 정보 가져오기
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeekNumber = currentDate.get(weekFields.weekOfMonth()); // 현재 시점의 몇 주차인지
        int currentMonth = currentDate.getMonthValue(); // 현재 시점의 월
        map.put("month", currentMonth);
        map.put("currentWeekNumber", currentWeekNumber);
        return map;
    }

    /**
     * "2023-08-30"
     *
     * @return 오늘일자의 년도, 달, 일자의 String을 알수있음.
     */
    public static String getStrYearMonthDay() {
        LocalDate currentDate = LocalDate.now();

        // 원하는 형식으로 날짜 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 형식에 맞게 날짜를 문자열로 변환
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }

    /**
     * 오늘 날짜기준으로
     * MONDAY = 0, TUESDAY = 1, ...
     *
     * @return
     */
    public static int getIndexDayOfWeek() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeekAtNow = currentDate.getDayOfWeek();
        int indexDayOfWeekAtNow = dayOfWeekAtNow.getValue() - 1; // MONDAY = 0, TUESDAY = 1, ...
        return indexDayOfWeekAtNow;
    }

    /**
     * 현제 오늘이 평일인지 아닌지 확인하는 메서드
     *
     * @return true: 평일, false: 주말
     */
    public static boolean isWeekDay() {
        int MONDAY = 1;
        int FRIDAY = 5;
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeekAtNow = currentDate.getDayOfWeek();

        // 요일이 월요일(1)부터 금요일(5) 사이인 경우에만 평일로 간주
        return (dayOfWeekAtNow.getValue() >= MONDAY) && (dayOfWeekAtNow.getValue() <= FRIDAY);
    }

    /**
     * 현제 오늘이 주말인지 아닌지 확인하는 메서드
     *
     * @return true: 주말, false: 평일
     */
    public static boolean isWeekendDay() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeekAtNow = currentDate.getDayOfWeek();

        // 요일이 토요일(6) 또는 일요일(7)인 경우에만 주말로 간주
        return dayOfWeekAtNow == DayOfWeek.SATURDAY || dayOfWeekAtNow == DayOfWeek.SUNDAY;
    }
}
