package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.TeamArticleUpdateReqDto;
import back.springbootdeveloper.seungchan.entity.TeamArticle;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.TeamArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamArticleService {
    private final TeamArticleRepository teamArticleRepository;

    @Transactional
    public void updateArticle(TeamArticleUpdateReqDto teamArticleUpdateReqDto, Long articleId) {
        final String updateTitle = teamArticleUpdateReqDto.getClubArticleUpdateTitle();
        final String updateContent = teamArticleUpdateReqDto.getClubArticleUpdateCotent();

        // TeamArticle ID을 이용해서 조회
        // 여기서 영속성 컨텍스트 1차캐싱구간에 저장
        TeamArticle searchTeamArticle = teamArticleRepository.findById(articleId)
                .orElseThrow(EntityNotFoundException::new);

        // TeamArticle 엔티티를 업데이트
        // 1차 캐싱구간의 엔티티를 변경
        searchTeamArticle.updateTitle(updateTitle);
        searchTeamArticle.updateContent(updateContent);

        // TeamArticle 엔티티 저장.
        //  // 1차 캐싱구간의 엔티티를 DB에 동기화.
        teamArticleRepository.save(searchTeamArticle);
    }
}
