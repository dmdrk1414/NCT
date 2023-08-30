package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.NumOfTodayAttendence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumOfTodayAttendenceRepository extends JpaRepository<NumOfTodayAttendence, Long> {
}
