package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DatabaseService {

  private final AttendanceStatusRepository attendanceStatusRepository;
  private final AttendanceTimeRepository attendanceTimeRepository;
  private final NumOfTodayAttendenceRepository numOfTodayAttendenceRepository;
  private final PeriodicDataRepository periodicDataRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final SuggestionRepository suggestionRepository;
  private final TempUserRepository tempUserRepository;
  private final UserRepository userRepository;
  private final UserUtilRepository userUtilRepository;
  private final NoticeRepository noticeRepository;

  public void deleteAllDatabase() {
    attendanceStatusRepository.deleteAll();
    attendanceTimeRepository.deleteAll();
    numOfTodayAttendenceRepository.deleteAll();
    periodicDataRepository.deleteAll();
    refreshTokenRepository.deleteAll();
    suggestionRepository.deleteAll();
    tempUserRepository.deleteAll();
    userRepository.deleteAll();
    userUtilRepository.deleteAll();
    noticeRepository.deleteAll();
  }

  @Transactional
  public void deleteUser(Long userId) {
    this.attendanceStatusRepository.deleteByUserId(userId);
    this.attendanceTimeRepository.deleteByUserId(userId);
    this.periodicDataRepository.deleteByUserId(userId);
    this.userRepository.deleteById(userId);
    this.userUtilRepository.deleteByUserId(userId);
  }
}
