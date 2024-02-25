package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.ClubMemberArticlesResDto;
import back.springbootdeveloper.seungchan.dto.response.MyClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubArticleRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubArticleService {
    private final ClubArticleRepository clubArticleRepository;

    public ClubMemberArticlesResDto getClubMemberArticlesResDto(Long clubMemberId) {
        List<MyClubArticle> myClubArticles = new ArrayList<>();
        List<ClubArticle> clubArticles = clubArticleRepository.findAllByClubMemberId(clubMemberId);

        clubArticles.forEach(clubArticle ->
                myClubArticles.add(new MyClubArticle(clubArticle))
        );

        return ClubMemberArticlesResDto.builder()
                .myClubArticles(myClubArticles)
                .build();
    }
}
