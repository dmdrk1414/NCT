package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubDetailPageService {
    private final ClubMemberRepository clubMemberRepository;
    private final MemberRepository memberRepository;

    /**
     * 주어진 클럽의 휴면 회원들의 전체 이름을 가져와서 반환합니다.
     *
     * @param clubId 클럽의 고유 ID
     * @return 휴면 회원들의 전체 이름으로 이루어진 문자열 리스트
     */
    public List<String> getAllDormancyMemberNamesOfClub(Long clubId) {
        List<ClubMember> dormantClubMembers = this.clubMemberRepository.findAllByClubIdAndClubGradeId(clubId, CLUB_GRADE.DORMANT.getId());
        List<Member> dormantMembers = getMembersFromClubMember(dormantClubMembers);

        return getFullNamesFromMembers(dormantMembers);
    }

    /**
     * 주어진 회원 객체 리스트에서 각 회원의 전체 이름을 추출하여 문자열 리스트로 반환합니다.
     *
     * @param members 전체 이름을 추출할 회원 객체 리스트
     * @return 각 회원의 전체 이름으로 이루어진 문자열 리스트
     */
    private List<String> getFullNamesFromMembers(List<Member> members) {
        return members.stream()
                .map(Member::getFullName)
                .toList();
    }

    /**
     * 주어진 ClubMember list 부터 실제 Member 객체을 반환
     *
     * @param clubMembers ClubMember list
     * @return Member List 반환
     */
    private List<Member> getMembersFromClubMember(List<ClubMember> clubMembers) {
        return clubMembers.stream()
                .map(clubMember -> memberRepository.findById(clubMember.getMemberId())
                        .orElseThrow(EntityNotFoundException::new))
                .toList();
    }
}
