package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.DateConstants;
import back.springbootdeveloper.seungchan.dto.request.UserEachAttendanceControlReqDto;
import back.springbootdeveloper.seungchan.dto.response.UserControlResDto;
import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.AttendanceTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class AttendanceTimeService {

  private static final Boolean FALSE_EXCEPTION = false;
  private static final Boolean TRUE_EXCEPTION = true;
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
  @Transactional
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
  public void updateExceptionAttendance(long userId) {
    AttendanceTime attendanceTime = attendanceTimeRepository.findByUserId(userId);
    boolean isException = attendanceTime.isExceptonAttendance();

    if (isException) {
      attendanceTimeRepository.updateException(userId, FALSE_EXCEPTION);
      return;
    }
    attendanceTimeRepository.updateException(userId, TRUE_EXCEPTION);
  }

  public boolean findExceptionAttendance(long id) {
    AttendanceTime attendanceTime = attendanceTimeRepository.findByUserId(id);
    if (attendanceTime == null) {
      throw new EntityNotFoundException();
    }

    return attendanceTime.isExceptonAttendance();
  }

  public AttendanceTime save(AttendanceTime attendanceTime) {
    return attendanceTimeRepository.save(attendanceTime);
  }

  public AttendanceTime findByUserId(Long userId) {
    return attendanceTimeRepository.findByUserId(userId);
  }
}
