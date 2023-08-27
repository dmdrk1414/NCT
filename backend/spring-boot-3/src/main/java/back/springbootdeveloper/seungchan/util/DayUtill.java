package back.springbootdeveloper.seungchan.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DayUtill {
    public static DayOfWeek getDayOfWeekAtNow(String dayStr) {
        int year = DayUtill.getYearFromDayStr(dayStr);
        int month = DayUtill.getMonthFromDayStr(dayStr);
        int dayOfMonth = DayUtill.getDayFromDayStr(dayStr);

        // LocalDate 객체를 생성합니다.
        LocalDate date = LocalDate.of(year, month, dayOfMonth);

        // 요일을 얻습니다.
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // TODO: 8/27/23 다시 변경
//        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
        if (dayOfWeek != DayOfWeek.SATURDAY) {
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
}
