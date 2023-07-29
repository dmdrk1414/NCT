package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.AttendanceStatus;
import back.springbootdeveloper.seungchan.domain.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Long> {
    AttendanceStatus findByUserId(Long userId);

    @Modifying
    @Query("UPDATE AttendanceStatus a SET a.weeklyData = :weeklyData WHERE a.userId = :userId")
    void updateWeeklyDataByUserId(Long userId, String weeklyData);
}
