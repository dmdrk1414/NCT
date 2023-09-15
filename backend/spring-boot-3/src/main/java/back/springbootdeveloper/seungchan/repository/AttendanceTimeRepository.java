package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceTimeRepository extends JpaRepository<AttendanceTime, Long> {
    AttendanceTime findByUserId(Long id);
}
