package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubIntroduceImage;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.testutills.TestMakeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class entityMappingTest {
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final ClubIntroduceImageRepository clubIntroduceImageRepository;

    @Autowired
    entityMappingTest(MemberRepository memberRepository, ClubMemberRepository clubMemberRepository, ClubRepository clubRepository, ClubIntroduceImageRepository clubIntroduceImageRepository) {
        this.memberRepository = memberRepository;
        this.clubMemberRepository = clubMemberRepository;
        this.clubRepository = clubRepository;
        this.clubIntroduceImageRepository = clubIntroduceImageRepository;
    }

    @BeforeEach
    void setUp() {
        this.clubMemberRepository.deleteAll();
        this.memberRepository.deleteAll();
        this.clubRepository.deleteAll();
    }

    @Test
    void Member_ClubMember_Club_ClubIntroduceImage_매핑_저장_학습_테스트_1() throws Exception {
        // Club
        Club club = TestMakeEntity.createSampleClub(0);

        // ClubIntroduceImage
        ClubIntroduceImage clubIntroduceImage_0 = TestMakeEntity.createSampleClubIntroduceImage(0);
        ClubIntroduceImage clubIntroduceImage_1 = TestMakeEntity.createSampleClubIntroduceImage(1);
        ClubIntroduceImage clubIntroduceImage_2 = TestMakeEntity.createSampleClubIntroduceImage(2);

        // Club - ClubIntroduceImage의 관계 설정
        club.addClubIntroduceImage(clubIntroduceImage_0);
        club.addClubIntroduceImage(clubIntroduceImage_1);
        club.addClubIntroduceImage(clubIntroduceImage_2);

        Club entityClub = clubRepository.save(club);

        // ============================================ club 등록 완료 ============================

        // Member
        Member member = TestMakeEntity.createSampleMember(0);
        Member entityMember = memberRepository.save(member);


        // ClubMember
        ClubMember clubMember = new ClubMember();


        // Member와 ClubMember의 관계 설정
        clubMember.setMember(entityMember);
        clubMember.setClub(entityClub);

        // Member 엔티티를 다시 저장하여 영속 상태를 유지
        clubMemberRepository.save(clubMember);
    }

    @Test
    void sdf() throws Exception {
    }
}