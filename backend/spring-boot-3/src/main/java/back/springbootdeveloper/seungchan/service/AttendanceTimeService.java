package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.Constant.DateConstants;
import back.springbootdeveloper.seungchan.dto.request.UserEachAttendanceControlReqDto;
import back.springbootdeveloper.seungchan.dto.response.UserControlResDto;
import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.repository.AttendanceTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class AttendanceTimeService {
    @Autowired
    private AttendanceTimeRepository attendanceTimeRepository;

    public UserControlResDto findUserControlResById(Long id) {
        AttendanceTime attendanceTime = attendanceTimeRepository.findByUserId(id);
        return new UserControlResDto(attendanceTime);
    }

    public void saveNewUser(UserInfo newUser) {
        AttendanceTime attendanceTime = new AttendanceTime(newUser);
        attendanceTimeRepository.save(attendanceTime);
    }

    /**
     * attendance time DB에 요일별 개인 시간을 업데이트 한다.
     *
     * @param userEachAttendanceControlReqDto
     * @param userId
     */
    public void updateAttendanceTime(UserEachAttendanceControlReqDto userEachAttendanceControlReqDto, Long userId) {
        List<String> attendanceTimes = userEachAttendanceControlReqDto.getAttendanceTimes();
        if (attendanceTimes.size() >= 5) {
            attendanceTimeRepository.updateAttendanceTime(
                    attendanceTimes.get(DateConstants.MONDAY.getIndex()), // Monday
                    attendanceTimes.get(DateConstants.TUESDAY.getIndex()), // Tuesday
                    attendanceTimes.get(DateConstants.WEDNESDAY.getIndex()), // Wednesday
                    attendanceTimes.get(DateConstants.THURSDAY.getIndex()), // Thursday
                    attendanceTimes.get(DateConstants.FRIDAY.getIndex()), // Friday
                    userId
            );
        }
    }


    public List<AttendanceTime> findAll() {
        return attendanceTimeRepository.findAll();
    }

    public void updateExceptionAttendance(long id) {
        AttendanceTime attendanceTime = attendanceTimeRepository.findByUserId(id);
        boolean isException = attendanceTime.isExceptonAttendance();
        boolean isFalseException = false;
        boolean isTrueException = true;
        Long userId = id;

        if (isException) {
            attendanceTimeRepository.updateException(userId, isFalseException);
        } else {
            attendanceTimeRepository.updateException(userId, isTrueException);
        }
    }

    public boolean findExceptionAttendance(long id) {
        AttendanceTime attendanceTime = attendanceTimeRepository.findByUserId(id);
        return attendanceTime.isExceptonAttendance();
    }
}
