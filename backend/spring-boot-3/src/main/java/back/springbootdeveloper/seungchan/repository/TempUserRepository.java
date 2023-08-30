package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TempUserRepository extends JpaRepository<TempUser, Long> {
    Optional<TempUser> findByEmail(String email);

    @Transactional
    void deleteByEmail(String emailOfNewUser);
}
