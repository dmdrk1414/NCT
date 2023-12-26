package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.PeriodicData;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.repository.PeriodicDataRepository;
import back.springbootdeveloper.seungchan.util.DayUtill;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
public class PeriodicDataService {
    private final PeriodicDataRepository periodicDataRepository;

    public void saveNewUser(UserInfo newUser) {
        String basicWeeklyData = "[0,0,0,0,0]";
        String basicMonth = "";

        PeriodicData periodicDataOfNewUser = PeriodicData.builder()
                .userId(newUser.getId())
                .name(newUser.getName())
                .weeklyData(basicWeeklyData)
                .thisMonth(basicMonth)
                .previousMonth(basicMonth)
                .build();

        periodicDataRepository.save(periodicDataOfNewUser);
    }

    public void updateWeeklyDataScheduled(List<AttendanceStatus> attendanceStatusList) {
        for (AttendanceStatus attendanceStatus : attendanceStatusList) {
            Long userId = attendanceStatus.getUserId();
            String weeklyData = attendanceStatus.getWeeklyData();
            periodicDataRepository.updateWeeklyDataScheduled(userId, weeklyData);
        }
    }

    public void updateThisMonthScheduled() {
        // Jackson ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Integer> monthAndWeekNumberMap = DayUtill.getMonthAndWeekNumberInMonthMap();
        String currentMonth = String.valueOf(monthAndWeekNumberMap.get("month"));
        String currentWeekNumber = String.valueOf(monthAndWeekNumberMap.get("currentWeekNumber"));

        List<PeriodicData> periodicDataList = periodicDataRepository.findAll();
        for (PeriodicData periodicData : periodicDataList) {
            String weeklyData = periodicData.getWeeklyData();
            StringBuilder thisMonthData = new StringBuilder(periodicData.getThisMonth());

            // thisMonthData = "4:[1,1,1,0,1] , 5:[1,1,1,0,1] , "
            thisMonthData = thisMonthData.append(currentWeekNumber + ":" + weeklyData + " , ");

            periodicDataRepository.updateThisMonthScheduled(String.valueOf(thisMonthData), periodicData.getUserId());
        }
    }

    public void updatePreviousMonthScheduled() {
        List<PeriodicData> periodicDataList = periodicDataRepository.findAll();
        for (PeriodicData periodicData : periodicDataList) {
            Long userId = periodicData.getUserId();
            String thisMonthData = periodicData.getThisMonth();
            periodicDataRepository.updatePreviousScheduled(thisMonthData, userId);
        }
    }

    public void resetThisMonth() {
        String resetThisMonth = "";
        periodicDataRepository.resetThisMonth(resetThisMonth);
    }

    public void resetPreviousMonth() {
        String resetPreviousMonth = "";
        periodicDataRepository.resetPreviousMonth(resetPreviousMonth);
    }

    public PeriodicData save(PeriodicData periodicData) {
        return periodicDataRepository.save(periodicData);
    }
}
