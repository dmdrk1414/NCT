package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = :#{#user.name}, u.phoneNum = :#{#user.phoneNum}, u.major = :#{#user.major}, u.gpa = :#{#user.gpa}, u.address = :#{#user.address}, u.specialtySkill = :#{#user.specialtySkill}, u.hobby = :#{#user.hobby}, u.mbti = :#{#user.mbti}, u.studentId = :#{#user.studentId}, u.birthDate = :#{#user.birthDate}, u.advantages = :#{#user.advantages}, u.disadvantage = :#{#user.disadvantage}, u.selfIntroduction = :#{#user.selfIntroduction}, u.photo = :#{#user.photo} WHERE u.id = :userId")
    void updateUser(Long userId, User user);
}