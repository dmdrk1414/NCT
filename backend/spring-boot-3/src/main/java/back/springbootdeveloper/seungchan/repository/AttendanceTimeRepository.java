package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AttendanceTimeRepository extends JpaRepository<AttendanceTime, Long> {
    AttendanceTime findByUserId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE AttendanceTime a SET a.attendanceTime = :attendanceTime WHERE a.userId = :userId")
    void updateAttemdanceTime(String attendanceTime, Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE AttendanceTime a SET a.isExceptonAttendance = :isChangeException WHERE a.userId = :userId")
    void updateException(long userId, boolean isChangeException);
}
