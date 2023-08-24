package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.domain.AttendanceStatus;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.dto.response.VacationsResponce;
import back.springbootdeveloper.seungchan.repository.AttendanceStatusRepository;
import back.springbootdeveloper.seungchan.util.Utill;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class AttendanceService {
    private final int attendanceOK = 1;

    @Autowired
    private AttendanceStatusRepository attendanceStatusRepository;

    private static boolean isContains(String vacationDates, String dateRequest) {
        return vacationDates.contains(dateRequest);
    }

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

        // [ 1, 1, 1, 0, 1 ]
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

    public void updateVacationDate(Long userId, VacationRequest vacationRequest) {
        AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
        String vacationDates = attendanceStatus.getVacationDates();
        String[] vacationDateOfRequest = vacationRequest.getPreVacationDate();

        String resultStr = attendanceStatus.getVacationDates();
        for (String dateRequest : vacationDateOfRequest) {
            if (!isContains(vacationDates, dateRequest)) {
                resultStr = resultStr + ", " + dateRequest;
            }
        }
        attendanceStatusRepository.updateVacationDatesByUserId(userId, resultStr);
    }

    public VacationsResponce findVacations(Long userId) {
        AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(userId);
        String absencesStr = attendanceStatus.getAbsenceDates();
        String vacationDateStr = attendanceStatus.getVacationDates();

        List<String> absences = Utill.arrFromStr(absencesStr);
        List<String> beforeVacationDate = new ArrayList<>();
        List<String> preVacationDate = new ArrayList<>();
        getBeforAndPreVacationDate(vacationDateStr, beforeVacationDate, preVacationDate);

        return new VacationsResponce(absences, beforeVacationDate, preVacationDate);
    }

    private void getBeforAndPreVacationDate(String vacationDateStr, List<String> beforeVacationDate, List<String> preVacationDate) {
        List<String> vacationList = Utill.arrFromStr(vacationDateStr);
        String todayStr = getTodays();
        LocalDate todayDate = LocalDate.parse(todayStr);

        for (String dateString : vacationList) {
            LocalDate date = LocalDate.parse(dateString);
            if (date.isBefore(todayDate) || date.isEqual(todayDate)) {
                beforeVacationDate.add(dateString);
            } else if (date.isAfter(todayDate)) {
                preVacationDate.add(dateString);
            }
        }
    }

    private String getTodays() {
        // 오늘 날짜를 가져오기
        LocalDate today = LocalDate.now();
        // 년도, 월, 일 추출
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public void resetWeeklyData() {
        String resetWeeklyData = "[0,0,0,0,0]";
        attendanceStatusRepository.resetWeeklyData(resetWeeklyData);
    }

    public void resetAbsenceDates() {
        String resetAbsenceData = "";
        attendanceStatusRepository.resetAbsenceDate(resetAbsenceData);
    }

    public void resetVacationDates() {
        String resetVacationDate = "";
        attendanceStatusRepository.resetVacationDate(resetVacationDate);
    }

    public void saveNewUser(User newUser) {
        String basicDate = "";
        String basicWeeklyData = "[0,0,0,0,0]";
        AttendanceStatus attendanceStatusOfNewUser = AttendanceStatus.builder()
                .userId(newUser.getId())
                .name(newUser.getName())
                .vacationDates(basicDate)
                .absenceDates(basicDate)
                .weeklyData(basicWeeklyData)
                .build();

        attendanceStatusRepository.save(attendanceStatusOfNewUser);
    }

    public List<AttendanceStatus> findAll() {
        return attendanceStatusRepository.findAll();
    }
}
