package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.TeamArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamArticleRepository extends JpaRepository<TeamArticle, Long> {

}
