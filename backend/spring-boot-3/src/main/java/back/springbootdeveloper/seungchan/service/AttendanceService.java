package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.domain.AttendanceStatus;
import back.springbootdeveloper.seungchan.domain.NumOfTodayAttendence;
import back.springbootdeveloper.seungchan.repository.AttendanceStatusRepository;
import back.springbootdeveloper.seungchan.repository.NumOfTodayAttendenceRepository;
import back.springbootdeveloper.seungchan.util.DayUtill;
import back.springbootdeveloper.seungchan.util.Utill;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class AttendanceService {
    private final int attendanceOK = 1;

    @Autowired
    private AttendanceStatusRepository attendanceStatusRepository;

    @Transactional
    public void UpdateweeklyData(int indexDay, Long userId) {
        // [ 1, 1, 1, 0, -1 ]
        AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
        String weeklyData = attendanceStatus.getWeeklyData();

        String updateWeeklyData = updateOfWeeklyData(weeklyData, indexDay);

        attendanceStatusRepository.updateWeeklyDataByUserId(userId, updateWeeklyData);
    }

    private String updateOfWeeklyData(String weeklyData, int indexDay) {
        // "[ 1, 1, 1, 0, -1 ]"
        JSONArray jsonArray = new JSONArray(weeklyData);
        int[] intArray = new int[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            intArray[i] = jsonArray.getInt(i);
        }

        intArray[indexDay] = attendanceOK;

        // [ 1, 1, 1, 0, -1 ]
        JSONArray resultJsonArray = arrayToJSONArray(intArray);
        return resultJsonArray.toString();
    }

    public JSONArray arrayToJSONArray(int[] arr) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < arr.length; i++) {
            jsonArray.put(arr[i]);
        }
        return jsonArray;
    }
}
