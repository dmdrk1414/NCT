package back.springbootdeveloper.seungchan.testutills;

import back.springbootdeveloper.seungchan.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDatabases {
    @Autowired
    private AttendanceStatusRepository attendanceStatusRepository;
    @Autowired
    private AttendanceTimeRepository attendanceTimeRepository;
    @Autowired
    private NumOfTodayAttendenceRepository numOfTodayAttendenceRepository;
    @Autowired
    private PeriodicDataRepository periodicDataRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private TempUserRepository tempUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserUtilRepository userUtilRepository;
    
    public void deleteAllOnDatabase() {
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
