package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.UserUtill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserUtilRepository extends JpaRepository<UserUtill, Long> {
    UserUtill findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE UserUtill u SET u.cntVacation = :cntVacation WHERE u.userId = :userId")
    void updateCntVacationUserUtilData(Long userId, int cntVacation);
}
