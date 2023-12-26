package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE UserInfo u SET u.name = :#{#user.name}, u.phoneNum = :#{#user.phoneNum}, u.major = :#{#user.major}, u.gpa = :#{#user.gpa}, u.address = :#{#user.address}, u.specialtySkill = :#{#user.specialtySkill}, u.hobby = :#{#user.hobby}, u.mbti = :#{#user.mbti}, u.studentId = :#{#user.studentId}, u.birthDate = :#{#user.birthDate}, u.advantages = :#{#user.advantages}, u.disadvantage = :#{#user.disadvantage}, u.selfIntroduction = :#{#user.selfIntroduction}, u.photo = :#{#user.photo}, u.email = :#{#user.email} WHERE u.id = :userId")
    void updateUser(Long userId, UserInfo user);

    Optional<UserInfo> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserInfo u SET  u.id = :#{#id} WHERE u.id = :userId")
    void updateId(Long userId, Long id);
}