package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.NumOfTodayAttendence;
import back.springbootdeveloper.seungchan.repository.NumOfTodayAttendenceRepository;
import back.springbootdeveloper.seungchan.util.DayUtill;
import back.springbootdeveloper.seungchan.util.Utill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class NumOfTodayAttendenceService {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private NumOfTodayAttendenceRepository numOfTodayAttendenceRepository;

    public boolean checkAttendanceNumber(String numOfAttendance, Long userId) {
        // 데이터 베이스에서 임시 번호를 저장한후에
        // 맞는지 아닌지 확인한다.
        List<NumOfTodayAttendence> numAttendencelist = numOfTodayAttendenceRepository.findAll();
        int sizeOfList = numAttendencelist.size();
        NumOfTodayAttendence numOfTodayAttendenceAtNow = numAttendencelist.get(sizeOfList - 1);
        String checkNum = numOfTodayAttendenceAtNow.getCheckNum();
        String dayStr = numOfTodayAttendenceAtNow.getDay();

        if (Utill.isEqualStr(numOfAttendance, checkNum)) {
            DayOfWeek dayOfWeekAtNow = DayUtill.getDayOfWeekAtNow(dayStr);
            int indexDayOfWeekAtNow = dayOfWeekAtNow.getValue() - 1; // - 1을 해야한다. MONDAY = 1 이기때문에
            attendanceService.UpdateweeklyData(indexDayOfWeekAtNow, userId);
            return true;
        }
        return false;
    }

    public void save(String day, int randomNum) {
        NumOfTodayAttendence numOfTodayAttendence = NumOfTodayAttendence.builder()
                .day(day)
                .checkNum(String.valueOf(randomNum))
                .build();
        numOfTodayAttendenceRepository.save(numOfTodayAttendence);
    }

    public void deleteAll() {
        numOfTodayAttendenceRepository.deleteAll();
    }

    public NumOfTodayAttendence findNumOfTodayAttendenceAtNow() {
        List<NumOfTodayAttendence> numAttendencelist = numOfTodayAttendenceRepository.findAll();
        int indexOfLast = numAttendencelist.size() - 1;
        return numAttendencelist.get(indexOfLast);
    }
}
