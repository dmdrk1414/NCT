package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.UserUtill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserUtilRepository extends JpaRepository<UserUtill, Long> {
    UserUtill findByUserId(Long userId);
}
