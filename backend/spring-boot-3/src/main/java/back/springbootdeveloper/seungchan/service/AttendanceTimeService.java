package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.DateConstants;
import back.springbootdeveloper.seungchan.dto.request.UserEachAttendanceControlReqDto;
import back.springbootdeveloper.seungchan.dto.response.UserControlResDto;
import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.repository.AttendanceTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     */
    public void updateAttendanceTime(UserEachAttendanceControlReqDto reqDto, Long userId) {
        attendanceTimeRepository.updateAttendanceTime(
                reqDto.getMondayAttendanceTime(),
                reqDto.getTuesdayAttendanceTime(),
                reqDto.getWednesdayAttendanceTime(),
                reqDto.getThursdayAttendanceTime(),
                reqDto.getFridayAttendanceTime(),
                userId
        );
    }


    public List<AttendanceTime> findAll() {
        return attendanceTimeRepository.findAll();
    }

    @Transactional
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

    public AttendanceTime save(AttendanceTime attendanceTime) {
        return attendanceTimeRepository.save(attendanceTime);
    }

    public AttendanceTime findByUserId(Long userId) {
        return attendanceTimeRepository.findByUserId(userId);
    }
}
