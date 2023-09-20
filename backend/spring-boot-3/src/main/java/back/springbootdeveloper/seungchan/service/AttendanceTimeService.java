package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.UserControlRequest;
import back.springbootdeveloper.seungchan.dto.response.UserControlResponse;
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

    public UserControlResponse findUserControlResById(Long id) {
        AttendanceTime attendanceTime = attendanceTimeRepository.findByUserId(id);
        return new UserControlResponse(attendanceTime);
    }

    public void saveNewUser(UserInfo newUser) {
        AttendanceTime attendanceTime = new AttendanceTime(newUser);
        attendanceTimeRepository.save(attendanceTime);
    }

    public void updateAttendanceTime(UserControlRequest userControlRequest, Long userId) {
        String attendanceTime = userControlRequest.getAttendanceTime();
        attendanceTimeRepository.updateAttemdanceTime(attendanceTime, userId);
    }

    public List<AttendanceTime> findAll() {
        return attendanceTimeRepository.findAll();
    }
}
