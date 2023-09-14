package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceTimeRepository extends JpaRepository<AttendanceTime, Long> {
}
