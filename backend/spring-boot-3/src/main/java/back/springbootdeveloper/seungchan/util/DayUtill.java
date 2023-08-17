package back.springbootdeveloper.seungchan.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;

public class DayUtill {
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
}
