package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

    @Autowired
    public DatabaseService(AttendanceStatusRepository attendanceStatusRepository, AttendanceTimeRepository attendanceTimeRepository, NumOfTodayAttendenceRepository numOfTodayAttendenceRepository, PeriodicDataRepository periodicDataRepository, RefreshTokenRepository refreshTokenRepository, SuggestionRepository suggestionRepository, TempUserRepository tempUserRepository, UserRepository userRepository, UserUtilRepository userUtilRepository) {
        this.attendanceStatusRepository = attendanceStatusRepository;
        this.attendanceTimeRepository = attendanceTimeRepository;
        this.numOfTodayAttendenceRepository = numOfTodayAttendenceRepository;
        this.periodicDataRepository = periodicDataRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.suggestionRepository = suggestionRepository;
        this.tempUserRepository = tempUserRepository;
        this.userRepository = userRepository;
        this.userUtilRepository = userUtilRepository;
    }

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
    }
}
