package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.entity.VacationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VacationTokenRepository extends JpaRepository<VacationToken, Long> {

}
