package back.springbootdeveloper.seungchan.service;


import back.springbootdeveloper.seungchan.entity.Suggestions;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteRequest;
import back.springbootdeveloper.seungchan.repository.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@Service
public class SuggestionService {
    private static final Boolean NOT_CHECK = false;
    private static final Boolean TRUE_CHECK = true;
    private final SuggestionRepository suggestionRepository;


    public List<Suggestions> findAll() {
        return suggestionRepository.findAll();
    }

    public Suggestions save(SuggestionWriteRequest suggestionWriteRequest) {
        return suggestionRepository.save(suggestionWriteRequest.toEntity());
    }

    /**
     * 해당 유저의 건의 게시판 check을 toggle 해준다.
     *
     * @param id
     * @return
     */
    public Boolean checkToggle(Long id) {
        Suggestions suggestions = suggestionRepository.findById(id).get();
        Boolean check = suggestions.isCheck();

        if (check) {
            suggestionRepository.updateByIdCheck(id, NOT_CHECK);
            return suggestionRepository.findById(id).get().isCheck();
        }
        suggestionRepository.updateByIdCheck(id, TRUE_CHECK);
        return suggestionRepository.findById(id).get().isCheck();
    }
}
