package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.domain.AttendanceStatus;
import back.springbootdeveloper.seungchan.domain.PeriodicData;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.repository.PeriodicDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
public class PeriodicDataService {
    private final PeriodicDataRepository periodicDataRepository;

    public void saveNewUser(User newUser) {
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
}
