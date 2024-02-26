package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.UpdateClubArticlePutDto;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
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
    private final ClubMemberRepository clubMemberRepository;

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

    /**
     * 주어진 회원 ID, 클럽 ID, 게시글 ID를 기준으로 해당 회원이 해당 클럽의 게시글 작성자인지 여부를 확인합니다.
     *
     * @param memberId  회원 ID
     * @param clubId    클럽 ID
     * @param articleId 게시글 ID
     * @return 해당 회원이 해당 클럽의 게시글 작성자이면 true, 아니면 false
     */
    public Boolean isAuthor(Long memberId, Long clubId, Long articleId) {
        final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId).orElseThrow(EntityNotFoundException::new);
        final ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);

        return isSame(clubMember, clubArticle);
    }

    /**
     * 주어진 클럽 ID, 회원 ID 및 게시글 ID에 대응하는 클럽 회원과 게시글을 찾은 다음, 주어진 DTO에 있는 정보로 게시글을 업데이트하고 저장합니다.
     *
     * @param clubId                  클럽 ID
     * @param memberId                회원 ID
     * @param articleId               게시글 ID
     * @param updateClubArticlePutDto 업데이트할 게시글의 정보가 담긴 DTO
     * @return 업데이트된 게시글
     * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
     */
    @Transactional
    public ClubArticle updateClubArticle(Long clubId, Long memberId, Long articleId, UpdateClubArticlePutDto updateClubArticlePutDto) {
        final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId).orElseThrow(EntityNotFoundException::new);
        final ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);

        clubArticle.updateTitle(updateClubArticlePutDto.getClubArticleUpdateTitle());
        clubArticle.updateContent(updateClubArticlePutDto.getClubArticleUpdateContent());
        final ClubArticle updateClubArticle = clubArticleRepository.save(clubArticle);

        if (updateClubArticle != null) {
            return updateClubArticle;
        }
        throw new EntityNotFoundException();
    }

    private Boolean isSame(ClubMember clubMember, ClubArticle clubArticle) {
        return clubArticle.getClubMemberId() == clubMember.getClubMemberId();
    }
}
