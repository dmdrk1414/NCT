package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceWeekDate;
import back.springbootdeveloper.seungchan.repository.AttendanceWeekDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceWeekDateService {

  private final AttendanceWeekDateRepository attendanceWeekDateRepository;

  @Transactional
  public void save(final AttendanceWeekDate attendanceWeekDate) {
    attendanceWeekDateRepository.save(attendanceWeekDate);
  }
}
