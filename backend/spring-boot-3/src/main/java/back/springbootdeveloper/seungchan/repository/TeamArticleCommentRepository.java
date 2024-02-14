package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.TeamArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamArticleCommentRepository extends JpaRepository<TeamArticleComment, Long> {

}
