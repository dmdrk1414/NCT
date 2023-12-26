package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Long> {
    AttendanceStatus findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE AttendanceStatus a SET a.weeklyData = :weeklyData WHERE a.userId = :userId")
    void updateWeeklyDataByUserId(Long userId, String weeklyData);

    @Transactional
    @Modifying
    @Query("UPDATE AttendanceStatus a SET a.vacationDates = :vacationDates WHERE a.userId = :userId")
    void updateVacationDatesByUserId(Long userId, String vacationDates);

    @Transactional
    @Modifying
    @Query("UPDATE AttendanceStatus u SET  u.id = :#{#id} WHERE u.id = :userId")
    void updateId(Long userId, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE AttendanceStatus u SET u.weeklyData = :resetWeeklyData")
    void resetWeeklyData(String resetWeeklyData);

    @Transactional
    @Modifying
    @Query("UPDATE AttendanceStatus u SET u.absenceDates = :resetAbsenceData")
    void resetAbsenceDate(String resetAbsenceData);

    @Transactional
    @Modifying
    @Query("UPDATE AttendanceStatus u SET u.vacationDates = :resetVacationDate")
    void resetVacationDate(String resetVacationDate);
}
