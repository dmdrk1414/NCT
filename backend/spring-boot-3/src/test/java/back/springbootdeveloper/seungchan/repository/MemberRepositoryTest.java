package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.testutills.TestMakeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest()
class MemberRepositoryTest {
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Autowired
    MemberRepositoryTest(MemberRepository memberRepository, ClubMemberRepository clubMemberRepository) {
        this.memberRepository = memberRepository;
        this.clubMemberRepository = clubMemberRepository;
    }

    @BeforeEach
    void setUp() {
        this.memberRepository.deleteAll();
        this.clubMemberRepository.deleteAll();
    }

    @Test
    void Member_저장_학습_테스트_1() throws Exception {
        Member member = TestMakeEntity.createSampleMember(0);
        Member entityMember = memberRepository.save(member);

        ClubMember entityClubMember = new ClubMember();

        // Member와 ClubMember의 관계 설정
        entityMember.addClubMembers(entityClubMember);

        // Member 엔티티를 다시 저장하여 영속 상태를 유지
        memberRepository.save(entityMember);
        clubMemberRepository.save(entityClubMember);
    }
}