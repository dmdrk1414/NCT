package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.*;
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

    /**
     * 주어진 clubMemberId에 해당하는 클럽 회원의 게시글을 가져와서 해당 회원이 작성한 게시글을 MyClubArticle 객체의 리스트로 변환한 후,
     * ClubMemberArticlesResDto 객체에 설정하여 반환합니다.
     *
     * @param clubMemberId 클럽 회원 ID
     * @return ClubMemberArticlesResDto 객체
     */
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
