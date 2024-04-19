package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.entity.VacationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
