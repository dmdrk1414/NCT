package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.testutills.TestMakeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class MemberRepositoryTest {
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;

    @Autowired
    MemberRepositoryTest(MemberRepository memberRepository, ClubMemberRepository clubMemberRepository, ClubRepository clubRepository) {
        this.memberRepository = memberRepository;
        this.clubMemberRepository = clubMemberRepository;
        this.clubRepository = clubRepository;
    }

    @BeforeEach
    void setUp() {
        this.clubMemberRepository.deleteAll();
        this.memberRepository.deleteAll();
        this.clubRepository.deleteAll();
    }

    @Test
    void Member_저장_학습_테스트_1() throws Exception {
        // Member
        Member member = TestMakeEntity.createSampleMember(0);
        Member entityMember = memberRepository.save(member);

        // Club
        Club club = TestMakeEntity.createSampleClub(0);
        Club entityClub = clubRepository.save(club);

        // ClubMember
        ClubMember clubMember = new ClubMember();


        // Member와 ClubMember의 관계 설정
        clubMember.setMember(entityMember);
        clubMember.setClub(entityClub);

        // Member 엔티티를 다시 저장하여 영속 상태를 유지
        clubMemberRepository.save(clubMember);
    }
}