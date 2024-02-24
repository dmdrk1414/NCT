package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.ClubGrade;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubGradeRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubGradeService {
    private final ClubMemberRepository clubMemberRepository;
    private final ClubGradeRepository clubGradeRepository;

    public ClubGrade findByClubIdAndMemberId(Long clubId, Long memberId) {
        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId).orElseThrow(EntityNotFoundException::new);
        return clubGradeRepository.findById(clubMember.getClubGradeId()).orElseThrow(EntityNotFoundException::new);
    }
}
