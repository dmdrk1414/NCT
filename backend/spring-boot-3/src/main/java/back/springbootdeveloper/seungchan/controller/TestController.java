package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.Suggestions;
import back.springbootdeveloper.seungchan.repository.SuggestionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
   private final SuggestionRepository suggestionRepository;

    public TestController(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @GetMapping("/test")
    public String testController(){
        suggestionRepository.save(Suggestions.builder()
                .classification("test classification")
                .title("test Title")
                .check(true)
                .build());
        return "Hello World";
    }
}
