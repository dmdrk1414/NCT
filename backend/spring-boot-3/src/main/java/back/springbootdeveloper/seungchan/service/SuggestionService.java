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
    private final SuggestionRepository suggestionRepository;


    public List<Suggestions> findAll() {
        return suggestionRepository.findAll();
    }

    public Suggestions save(SuggestionWriteRequest suggestionWriteRequest) {
        return suggestionRepository.save(suggestionWriteRequest.toEntity());
    }
}
