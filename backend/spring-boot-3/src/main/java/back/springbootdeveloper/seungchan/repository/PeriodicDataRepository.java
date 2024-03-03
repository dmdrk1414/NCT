package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.PeriodicData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PeriodicDataRepository extends JpaRepository<PeriodicData, Long> {

  @Transactional
  @Modifying
  @Query("UPDATE PeriodicData u SET u.weeklyData = :weeklyData WHERE u.userId = :userId")
  void updateWeeklyDataScheduled(Long userId, String weeklyData);

  @Transactional
  @Modifying
  @Query("UPDATE PeriodicData u SET u.thisMonth = :resultOfthisMonth WHERE u.userId = :userId")
  void updateThisMonthScheduled(String resultOfthisMonth, Long userId);

  @Transactional
  @Modifying
  @Query("UPDATE PeriodicData u SET u.previousMonth = :thisMonthData WHERE u.userId = :userId")
  void updatePreviousScheduled(String thisMonthData, Long userId);

  @Transactional
  @Modifying
  @Query("UPDATE PeriodicData u SET u.thisMonth = :resetThisMonth")
  void resetThisMonth(String resetThisMonth);

  @Transactional
  @Modifying
  @Query("UPDATE PeriodicData u SET u.previousMonth = :resetPreviousMonth")
  void resetPreviousMonth(String resetPreviousMonth);

  PeriodicData findByUserId(Long userId);

  void deleteByUserId(Long userId);
}

