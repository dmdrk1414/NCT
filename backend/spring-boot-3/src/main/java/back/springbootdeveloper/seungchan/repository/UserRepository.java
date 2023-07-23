package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
