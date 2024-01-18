package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.NumOfTodayAttendence;
import back.springbootdeveloper.seungchan.filter.exception.judgment.WeekendException;
import back.springbootdeveloper.seungchan.repository.NumOfTodayAttendenceRepository;
import back.springbootdeveloper.seungchan.util.DayUtill;
import back.springbootdeveloper.seungchan.util.Utill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class NumOfTodayAttendenceService {

    private static final Integer LAST_INDEX = 1;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private NumOfTodayAttendenceRepository numOfTodayAttendenceRepository;

    /**
     * 데이터 베이스에서 임시 번호를 저장한후에 맞는지 아닌지 확인한다.
     *
     * @return
     */
    public boolean checkAttendanceNumber(String numOfAttendance, Long userId) {
        // 주말 여부
        if (DayUtill.isWeekendDay()) {
            throw new WeekendException();
        }

        List<NumOfTodayAttendence> numAttendencelist = numOfTodayAttendenceRepository.findAll();
        int sizeOfList = numAttendencelist.size();
        NumOfTodayAttendence numOfTodayAttendenceAtNow = numAttendencelist.get(sizeOfList - 1);
        String checkNum = numOfTodayAttendenceAtNow.getCheckNum();
        String dayStr = numOfTodayAttendenceAtNow.getDay();

//        이미 휴가를 사용했다면 휴가를 사용할 수 없다
//        이미 출석을 하였다면 휴가을 사용할수 없다.
//        이미 휴가를 사용했다면 중복으로 사용할 수 없다.
        if (Utill.isEqualStr(numOfAttendance, checkNum) && availableAttendance(userId)) {
            DayOfWeek dayOfWeekAtNow = DayUtill.getDayOfWeekAtNow(dayStr);
            int indexDayOfWeekAtNow = dayOfWeekAtNow.getValue() - 1; // - 1을 해야한다. MONDAY = 1 이기때문에
            attendanceService.UpdateweeklyData(indexDayOfWeekAtNow, userId);
            return true;
        }
        return false;
    }

    public NumOfTodayAttendence save(String day, int randomNum) {
        NumOfTodayAttendence numOfTodayAttendence = NumOfTodayAttendence.builder()
                .day(day)
                .checkNum(String.valueOf(randomNum))
                .build();
        return numOfTodayAttendenceRepository.save(numOfTodayAttendence);
    }

    private Boolean availableAttendance(Long userId) {
        // 0인 경우에만 출석을 할수 있다.
        return attendanceService.available(userId);
    }

    public void deleteAll() {
        numOfTodayAttendenceRepository.deleteAll();
    }

    /**
     * 금일 출석 번호 조회
     *
     * @return
     */
    public NumOfTodayAttendence findNumOfTodayAttendenceAtNow() {
        List<NumOfTodayAttendence> numAttendencelist = numOfTodayAttendenceRepository.findAll();
        int indexOfLast = numAttendencelist.size() - LAST_INDEX;

        return numAttendencelist.get(indexOfLast);
    }
}
