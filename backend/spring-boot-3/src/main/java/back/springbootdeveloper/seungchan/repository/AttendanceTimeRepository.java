package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AttendanceTimeRepository extends JpaRepository<AttendanceTime, Long> {

  AttendanceTime findByUserId(Long id);

  @Transactional
  @Modifying
  @Query("UPDATE AttendanceTime a SET a.monday = :monday, a.tuesday = :tuesday, a.wednesday = :wednesday, a.thursday = :thursday, a.friday = :friday WHERE a.userId = :userId")
  void updateAttendanceTime(String monday, String tuesday, String wednesday, String thursday,
      String friday, Long userId);


  @Transactional
  @Modifying
  @Query("UPDATE AttendanceTime a SET a.isExceptonAttendance = :isChangeException WHERE a.userId = :userId")
  void updateException(long userId, boolean isChangeException);

  void deleteByUserId(Long userId);
}
