package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.PeriodicData;
import back.springbootdeveloper.seungchan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PeriodicDataRepository extends JpaRepository<PeriodicData, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE PeriodicData u SET u.weeklyData = :weeklyData WHERE u.userId = :userId")
    void updateWeeklyDataScheduled(Long userId, String weeklyData);

    @Transactional
    @Modifying
    @Query("UPDATE PeriodicData u SET u.thisMonth = :resultOfthisMonth WHERE u.userId = :userId")
    void updateThisMonthScheduled(String resultOfthisMonth, Long userId);
}

