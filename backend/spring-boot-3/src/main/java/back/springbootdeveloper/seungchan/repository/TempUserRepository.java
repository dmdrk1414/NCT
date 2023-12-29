package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TempUserRepository extends JpaRepository<TempUser, Long> {
    Optional<TempUser> findByEmail(String email);

    @Transactional
    void deleteByEmail(String emailOfNewUser);

    Boolean existsByEmail(String email);
}
