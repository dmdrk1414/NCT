package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.testutills.TestMakeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class entityMappingTest_2 {
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final ClubIntroduceImageRepository clubIntroduceImageRepository;
    private final ClubGradeRepository clubGradeRepository;
    private final ClubArticleRepository clubArticleRepository;
    private final AttendanceNumberRepository attendanceNumberRepository;
    private final ClubControlRepository clubControlRepository;
    private final AttendanceWeekDateRepository attendanceWeekDateRepository;
    private final AttendanceSateRepository attendanceSateRepository;
    private final VacationTokenRepository vacationTokenRepository;
    private final ClubMemberInformationRepository clubMemberInformationRepository;

    CLUB_ARTICLE_CLASSIFICATION SUGGESTION = CLUB_ARTICLE_CLASSIFICATION.SUGGESTION;
    CLUB_ARTICLE_CLASSIFICATION FREEDOM = CLUB_ARTICLE_CLASSIFICATION.FREEDOM;
    CLUB_ARTICLE_CLASSIFICATION CONFIDENTIAL = CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL;

    @Autowired
    entityMappingTest_2(MemberRepository memberRepository, ClubMemberRepository clubMemberRepository, ClubRepository clubRepository, ClubIntroduceImageRepository clubIntroduceImageRepository, ClubGradeRepository clubGradeRepository, ClubArticleRepository clubArticleRepository, AttendanceNumberRepository attendanceNumberRepository, ClubControlRepository clubControlRepository, AttendanceWeekDateRepository attendanceWeekDateRepository, AttendanceSateRepository attendanceSateRepository, VacationTokenRepository vacationTokenRepository, ClubMemberInformationRepository clubMemberInformationRepository) {
        this.memberRepository = memberRepository;
        this.clubMemberRepository = clubMemberRepository;
        this.clubRepository = clubRepository;
        this.clubIntroduceImageRepository = clubIntroduceImageRepository;
        this.clubGradeRepository = clubGradeRepository;
        this.clubArticleRepository = clubArticleRepository;
        this.attendanceNumberRepository = attendanceNumberRepository;
        this.clubControlRepository = clubControlRepository;
        this.attendanceWeekDateRepository = attendanceWeekDateRepository;
        this.attendanceSateRepository = attendanceSateRepository;
        this.vacationTokenRepository = vacationTokenRepository;
        this.clubMemberInformationRepository = clubMemberInformationRepository;
    }

    @BeforeEach
    void setUp() {
        this.clubMemberRepository.deleteAll();
        this.memberRepository.deleteAll();
        this.clubRepository.deleteAll();
        this.clubArticleRepository.deleteAll();
        this.attendanceSateRepository.deleteAll();
        this.clubMemberInformationRepository.deleteAll();
    }

    @Test
    void 매핑_저장_학습_테스트_1() throws Exception {
        Integer number = 0;
        // ================================== Club 1.Club 등록 ============================
        Club club = applyClub___AttendanceNumber_ClubIntroduceImage(number);

        // ================================== Club 2.AttendanceSate 등록 ============================
        AttendanceSate attendanceSate = mapping_AttendanceSate___AttendanceCheckTime_AttendanceWeekDate_VacationToken();

        // ================================== Club 3.ClubControl 생성 ============================
        ClubControl clubControl = mapping_ClubControl___VacattionTokenControl_AttendanceWeek();

        // ================================== Club 4.Club - ClubControl 등록 ============================
        club = mappring_Club_ClubControl(club, clubControl);

        // ==================================== Club 5.ClubGrade 찾기 시작 ============================
        ClubGrade clubGradeLeader = clubGradeRepository.findByClubGrade(CLUB_GRADE.LEADER);


        // ================================= Member 20.Member 등록 시작 ============================
        Member member = applyMember(number);

        // ================================= Member 21.ClubMemberInformation 등록 시작 ============================
        ClubMemberInformation clubMemberInformation = applyClubMemberInformation(number);

        // ================================= Club - Member 30.ClubMember 등록 시작 ============================
        ClubMember clubMember = applyClubMember(member.getMemberId(), club.getClubId(), clubGradeLeader.getClubGradeId(), attendanceSate.getAttendanceStateId(),
                clubMemberInformation.getClubMemberInformationId());

        // ========================================= 40.ClubArticle - ClubArticleComment 3개 등록 시작 ========================
        // ================================= Club - Member -- ClubArticle 41.ClubArticle 매핑 시작 ============================
        ClubArticle clubArticle_suggestion = applyClubArticleCommentToClubArticle(SUGGESTION, number, clubMember);
    }

    private ClubMember applyClubMember(Long memberId, Long clubId, Long clubGradeId, Long attendanceStateId, Long clubMemberInformationId) {
        ClubMember clubMember = TestMakeEntity.createSampleClubMember(memberId, clubId, clubGradeId, attendanceStateId, clubMemberInformationId);

        return clubMemberRepository.save(clubMember);
    }

    private ClubMemberInformation applyClubMemberInformation(Integer number) {
        ClubMemberInformation clubMemberInformation = TestMakeEntity.createSampleClubMemberInformation(number);

        return clubMemberInformationRepository.save(clubMemberInformation);
    }

    /**
     * AttendanceState 매핑 관계
     *
     * @return
     */
    private AttendanceSate mapping_AttendanceSate___AttendanceCheckTime_AttendanceWeekDate_VacationToken() {
        AttendanceSate attendanceSate = new AttendanceSate();
        AttendanceCheckTime attendanceCheckTime = new AttendanceCheckTime();
        AttendanceWeekDate attendanceWeekDate = new AttendanceWeekDate();
        VacationToken vacationToken = new VacationToken();

        attendanceSate.setAttendanceCheckTime(attendanceCheckTime);
        attendanceSate.setAttendanceWeekDate(attendanceWeekDate);
        attendanceSate.setVacationToken(vacationToken);

        return attendanceSateRepository.save(attendanceSate);
    }

    private Club mappring_Club_ClubControl(Club entityClub, ClubControl clubControl) {
        entityClub.setClubControl(clubControl);

        return clubRepository.save(entityClub);
    }

    private ClubControl mapping_ClubControl___VacattionTokenControl_AttendanceWeek() {
        ClubControl clubControl = new ClubControl();
        VacationTokenControl vacationTokenControl = new VacationTokenControl();
        AttendanceWeek attendanceWeek = new AttendanceWeek();

        clubControl.setVacationTokenControl(vacationTokenControl);
        clubControl.setAttendanceWeek(attendanceWeek);

        return clubControlRepository.save(clubControl);
    }

    private Member applyMember(Integer number) {
        Member member = TestMakeEntity.createSampleMember(number);
        Member entityMember = memberRepository.save(member);
        return entityMember;
    }

    private Club applyClub___AttendanceNumber_ClubIntroduceImage(Integer number) {
        // Club
        Club club = TestMakeEntity.createSampleClub(number);

        // AttendanceNumber
        AttendanceNumber attendanceNumber = new AttendanceNumber();

        // Club - AttendanceNumber
        club.addAttendanceNumber(attendanceNumber);

        // ClubIntroduceImage
        ClubIntroduceImage clubIntroduceImage_0 = TestMakeEntity.createSampleClubIntroduceImage(0);
        ClubIntroduceImage clubIntroduceImage_1 = TestMakeEntity.createSampleClubIntroduceImage(1);
        ClubIntroduceImage clubIntroduceImage_2 = TestMakeEntity.createSampleClubIntroduceImage(2);

        // Club - ClubIntroduceImage의 관계 설정
        club.addClubIntroduceImage(clubIntroduceImage_0);
        club.addClubIntroduceImage(clubIntroduceImage_1);
        club.addClubIntroduceImage(clubIntroduceImage_2);

        return clubRepository.save(club);

    }

    private ClubArticle applyClubArticleCommentToClubArticle(CLUB_ARTICLE_CLASSIFICATION classification, Integer number, ClubMember clubMember) {
        // ClubArticle
        ClubArticle clubArticle = TestMakeEntity.createSampleClubArticle(classification, number, clubMember);

        // ClubArticleComment
        ClubArticleComment clubArticleComment_0 = TestMakeEntity.createSampleClubArticleComment(0);
        ClubArticleComment clubArticleComment_1 = TestMakeEntity.createSampleClubArticleComment(1);
        ClubArticleComment clubArticleComment_2 = TestMakeEntity.createSampleClubArticleComment(2);

        // ClubArticle - ClubArticleComment
        clubArticle.addClubArticleComment(clubArticleComment_0);
        clubArticle.addClubArticleComment(clubArticleComment_1);
        clubArticle.addClubArticleComment(clubArticleComment_2);


        return clubArticleRepository.save(clubArticle);
    }

}