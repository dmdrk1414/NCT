package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempUserRepository extends JpaRepository<TempUser, Long> {
    Optional<TempUser> findByEmail(String email);
}
