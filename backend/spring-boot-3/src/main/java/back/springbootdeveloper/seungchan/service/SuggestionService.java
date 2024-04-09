package back.springbootdeveloper.seungchan.service;


import back.springbootdeveloper.seungchan.entity.Suggestion;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteReqDto;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class SuggestionService {

  private static final Boolean NOT_CHECK = false;
  private static final Boolean TRUE_CHECK = true;
  private final SuggestionRepository suggestionRepository;


  public List<Suggestion> findAll() {
    List<Suggestion> suggestions = suggestionRepository.findAll();
    if (suggestions == null) {
      throw new EntityNotFoundException();
    }

    return suggestions;
  }

  @Transactional
  public Suggestion save(SuggestionWriteReqDto suggestionWriteRequest) {
    Suggestion suggestions = suggestionRepository.save(suggestionWriteRequest.toEntity());
    if (suggestions == null) {
      throw new EntityNotFoundException();
    }

    return suggestions;
  }

  /**
   * 해당 유저의 건의 게시판 check을 toggle 해준다.
   *
   * @param id
   * @return
   */
  @Transactional
  public void checkToggle(Long id) {
    Suggestion suggestions = suggestionRepository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
    Boolean check = suggestions.isCheck();
    if (check) {
      suggestionRepository.updateByIdCheck(id, NOT_CHECK);
      return;
    }
    suggestionRepository.updateByIdCheck(id, TRUE_CHECK);
  }

  public Suggestion findById(Long id) {
    Suggestion suggestion = suggestionRepository.findById(id).get();
    if (suggestion == null) {
      throw new EntityNotFoundException();
    }

    return suggestion;
  }
}
