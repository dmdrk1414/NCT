package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestions, Long> {
}
