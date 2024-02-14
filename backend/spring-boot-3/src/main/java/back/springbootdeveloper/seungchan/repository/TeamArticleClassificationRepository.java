package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.TeamArticleClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamArticleClassificationRepository extends JpaRepository<TeamArticleClassification, Long> {

}
