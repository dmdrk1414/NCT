package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.repository.AttendanceTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class AttendanceTimeService {
    private final AttendanceTimeRepository attendanceTimeRepository;

    public void saveNewUser(UserInfo newUser) {
        AttendanceTime attendanceTime = new AttendanceTime(newUser);
        attendanceTimeRepository.save(attendanceTime);
    }
}
