package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    List<ClubMember> findAllByClubIdAndClubGradeId(Long clubId, Integer clubGradeId);
}