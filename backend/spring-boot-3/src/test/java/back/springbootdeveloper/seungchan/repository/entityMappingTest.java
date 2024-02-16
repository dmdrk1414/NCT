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
class entityMappingTest {
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

    @Autowired
    entityMappingTest(MemberRepository memberRepository, ClubMemberRepository clubMemberRepository, ClubRepository clubRepository, ClubIntroduceImageRepository clubIntroduceImageRepository, ClubGradeRepository clubGradeRepository, ClubArticleRepository clubArticleRepository, AttendanceNumberRepository attendanceNumberRepository, ClubControlRepository clubControlRepository, AttendanceWeekDateRepository attendanceWeekDateRepository, AttendanceSateRepository attendanceSateRepository, VacationTokenRepository vacationTokenRepository) {
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
    }

    @BeforeEach
    void setUp() {
        this.clubArticleRepository.deleteAll();
        this.clubMemberRepository.deleteAll();
        this.memberRepository.deleteAll();
        this.attendanceNumberRepository.deleteAll();
        this.clubRepository.deleteAll();
        this.clubControlRepository.deleteAll();
        this.attendanceWeekDateRepository.deleteAll();
        this.attendanceSateRepository.deleteAll();
        this.clubControlRepository.deleteAll();
    }

    @Test
    void 매핑_저장_학습_테스트_1() throws Exception {
        // ============================================ Club 등록 시작 ============================
        Club entityClub_0 = applyClub(0);
        // ============================================ Club 등록 완료 ============================

        // ============================================ 출석 AttendanceNumber 등록 시작 ============================
        // 1:1
        applyAttendanceNumber_mapping_Club_AttendanceNumber(entityClub_0, new AttendanceNumber());
        // ============================================ 출석 AttendanceNumber 등록 완료 ============================

        // ==================================  AttendanceSate  매핑 시작 ============================
        // ==================================        |                  ============================
        // =====================  AttendanceWeekDate - VacationToken -VacationToken ============================
        AttendanceSate attendanceSate = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        // ==================================  AttendanceSate 매핑 완료 ============================


        // ================================ ClubControl - VacationTokenControl - AttendanceWeek 등록 ============================
        // 1:1
        ClubControl clubControl_0 = mapping_ClubControl_VacattionTokenControl_AttendanceWeek();
        // ============================================ ClubControl - VacationTokenControl 등록 완료 ============================

        // ============================================= Club - ClubControl 등록 ============================
        // 1:1
        entityClub_0 = mappring_Club_ClubControl(entityClub_0, clubControl_0);
        // ============================================= Club - ClubControl 등록 완료 ============================

        // ============================================ Member 등록 시작 ============================
        Member entityMember_0 = applyMember(0);
        Member entityMember_1 = applyMember(1);
        // ============================================ Member 등록 완료 ============================

        // ============================================ ClubGrade 찾기 시작 ============================
        ClubGrade clubGradeLeader = clubGradeRepository.findByClubGrade(CLUB_GRADE.LEADER);
        ClubGrade clubGradeMember = clubGradeRepository.findByClubGrade(CLUB_GRADE.MEMBER);
        // ============================================ ClubGrade 찾기 완료 ============================


        // ========================================= Member와 Club의 관계 설정 시작 ========================
        ClubMember entityClubMember = Mapping_Member_Club(entityClub_0, entityMember_0, clubGradeLeader);
        ClubMember entityClubMember_1 = Mapping_Member_Club(entityClub_0, entityMember_1, clubGradeMember);
        // ========================================= Member와 Club의 관계 설정 완료 ========================

        // ========================================= ClubArticle 등록 시작 ========================
        ClubArticle entityClubArticle_suggestion = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 0);
        ClubArticle entityClubArticle_suggestion_1 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 1);
        ClubArticle entityClubArticle_suggestion_2 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 2);
        // ========================================= ClubArticle 등록 완료 ========================


        // ========================================= ClubArticleComment 등록 시작 ========================
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, 0);
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, 1);
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, 2);
        // ========================================= ClubArticleComment 등록 완료 ========================

        // ========================================= Member와 Club와 ClubArticle의 관계 설정 시작 ========================
//        entityClubMember = Mapping_Member_Club(entityClubMember, entityClubArticle_suggestion);

        entityClubMember.addClubArticle(entityClubArticle_suggestion);
        entityClubMember.addClubArticle(entityClubArticle_suggestion_1);
        entityClubMember.addClubArticle(entityClubArticle_suggestion_2);
        clubArticleRepository.save(entityClubArticle_suggestion);
        clubArticleRepository.save(entityClubArticle_suggestion_1);
        clubArticleRepository.save(entityClubArticle_suggestion_2);
        entityClubMember = clubMemberRepository.save(entityClubMember);

        // ========================================= Member와 Club와 ClubArticle의 관계 설정 완료 ========================

        // ==================================  ClubMember - AttendanceState 매핑 시작 ============================
        entityClubMember = mapping_AttendanceState_ClubMember(entityClubMember, attendanceSate);
        // ==================================  ClubMember - AttendanceState 매핑 완료 ============================

        // ==================================  ClubMember - ClubMemberInformation 매핑 시작 ============================
        entityClubMember = mapping_ClubMemberInformation_ClubMember(entityClubMember, 0);
        // ==================================  ClubMember - AttendanceState 매핑 완료 ============================
    }

    @Test
    void 테이터_베이스_생성_테스트() throws Exception {
        데이터베이스_클럽_0_멤버_0_12_명_생성_함수();
    }

    void 데이터베이스_생성_함수(Integer number) {
        // ============================================ Club 등록 시작 ============================
        Club entityClub_0 = applyClub(number);
        // ============================================ Club 등록 완료 ============================

        // ============================================ 출석 AttendanceNumber 등록 시작 ============================
        // 1:1
        applyAttendanceNumber_mapping_Club_AttendanceNumber(entityClub_0, new AttendanceNumber());
        // ============================================ 출석 AttendanceNumber 등록 완료 ============================

        // ==================================  AttendanceSate  매핑 시작 ============================
        // ==================================        |                  ============================
        // =====================  AttendanceWeekDate - VacationToken -VacationToken ============================
        AttendanceSate attendanceSate = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        // ==================================  AttendanceSate 매핑 완료 ============================


        // ================================ ClubControl - VacationTokenControl - AttendanceWeek 등록 ============================
        // 1:1
        ClubControl clubControl_0 = mapping_ClubControl_VacattionTokenControl_AttendanceWeek();
        // ============================================ ClubControl - VacationTokenControl 등록 완료 ============================

        // ============================================= Club - ClubControl 등록 ============================
        // 1:1
        entityClub_0 = mappring_Club_ClubControl(entityClub_0, clubControl_0);
        // ============================================= Club - ClubControl 등록 완료 ============================

        // ============================================ Member 등록 시작 ============================
        Member entityMember_0 = applyMember(number);
        // ============================================ Member 등록 완료 ============================

        // ============================================ ClubGrade 찾기 시작 ============================
        ClubGrade clubGradeLeader = clubGradeRepository.findByClubGrade(CLUB_GRADE.LEADER);
        // ============================================ ClubGrade 찾기 완료 ============================


        // ========================================= Member와 Club의 관계 설정 시작 ========================
        ClubMember entityClubMember = Mapping_Member_Club(entityClub_0, entityMember_0, clubGradeLeader);
        // ========================================= Member와 Club의 관계 설정 완료 ========================

        // ========================================= ClubArticle 등록 시작 ========================
        ClubArticle entityClubArticle_suggestion = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, number);
        // ========================================= ClubArticle 등록 완료 ========================


        // ========================================= ClubArticleComment 등록 시작 ========================
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, number);
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, 1);
        entityClubArticle_suggestion = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion, 2);
        // ========================================= ClubArticleComment 등록 완료 ========================

        // ========================================= Member와 Club와 ClubArticle의 관계 설정 시작 ========================
        entityClubMember = Mapping_Member_Club(entityClubMember, entityClubArticle_suggestion);
        // ========================================= Member와 Club와 ClubArticle의 관계 설정 완료 ========================

        // ==================================  ClubMember - AttendanceState 매핑 시작 ============================
        entityClubMember = mapping_AttendanceState_ClubMember(entityClubMember, attendanceSate);
        // ==================================  ClubMember - AttendanceState 매핑 완료 ============================

        // ==================================  ClubMember - ClubMemberInformation 매핑 시작 ============================
        entityClubMember = mapping_ClubMemberInformation_ClubMember(entityClubMember, number);
        // ==================================  ClubMember - AttendanceState 매핑 완료 ============================
    }

    void 데이터베이스_클럽_0_멤버_0_12_명_생성_함수() {
        // ============================================ Club 등록 시작 ============================
        Club entityClub_0 = applyClub(0);
        // ============================================ Club 등록 완료 ============================

        // ============================================ 출석 AttendanceNumber 등록 시작 ============================
        // 1:1
        applyAttendanceNumber_mapping_Club_AttendanceNumber(entityClub_0, new AttendanceNumber());
        // ============================================ 출석 AttendanceNumber 등록 완료 ============================

        // ==================================  AttendanceSate  매핑 시작 ============================
        // ==================================        |                  ============================
        // =====================  AttendanceWeekDate - VacationToken -VacationToken ============================
        AttendanceSate attendanceSate_0 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_1 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_2 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_3 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_4 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_5 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_6 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_7 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_8 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_9 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_10 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_11 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        AttendanceSate attendanceSate_12 = mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken();
        // ==================================  AttendanceSate 매핑 완료 ============================


        // ================================ ClubControl - VacationTokenControl - AttendanceWeek 등록 ============================
        // 1:1
        ClubControl clubControl_0 = mapping_ClubControl_VacattionTokenControl_AttendanceWeek();
        // ============================================ ClubControl - VacationTokenControl 등록 완료 ============================

        // ============================================= Club - ClubControl 등록 ============================
        // 1:1
        entityClub_0 = mappring_Club_ClubControl(entityClub_0, clubControl_0);
        // ============================================= Club - ClubControl 등록 완료 ============================

        // ============================================ Member 등록 시작 ============================
        Member entityMember_0 = applyMember(0);
        Member entityMember_1 = applyMember(1);
        Member entityMember_2 = applyMember(2);
        Member entityMember_3 = applyMember(3);
        Member entityMember_4 = applyMember(4);
        Member entityMember_5 = applyMember(5);
        Member entityMember_6 = applyMember(6);
        Member entityMember_7 = applyMember(7);
        Member entityMember_8 = applyMember(8);
        Member entityMember_9 = applyMember(9);
        Member entityMember_10 = applyMember(10);
        Member entityMember_11 = applyMember(11);
        Member entityMember_12 = applyMember(12);
        // ============================================ Member 등록 완료 ============================

        // ============================================ ClubGrade 찾기 시작 ============================
        ClubGrade clubGradeLeader = clubGradeRepository.findByClubGrade(CLUB_GRADE.LEADER);
        ClubGrade clubGradeDeputyLeader = clubGradeRepository.findByClubGrade(CLUB_GRADE.DEPUTY_LEADER);
        ClubGrade clubGradeMember = clubGradeRepository.findByClubGrade(CLUB_GRADE.MEMBER);
        ClubGrade clubGradeDormant = clubGradeRepository.findByClubGrade(CLUB_GRADE.DORMANT);
        // ============================================ ClubGrade 찾기 완료 ============================


        // ========================================= Member와 Club의 관계 설정 시작 ========================
        ClubMember entityClubMember_0 = Mapping_Member_Club(entityClub_0, entityMember_0, clubGradeLeader);
        ClubMember entityClubMember_1 = Mapping_Member_Club(entityClub_0, entityMember_1, clubGradeDeputyLeader);
        ClubMember entityClubMember_2 = Mapping_Member_Club(entityClub_0, entityMember_2, clubGradeMember);
        ClubMember entityClubMember_3 = Mapping_Member_Club(entityClub_0, entityMember_3, clubGradeMember);
        ClubMember entityClubMember_4 = Mapping_Member_Club(entityClub_0, entityMember_4, clubGradeMember);
        ClubMember entityClubMember_5 = Mapping_Member_Club(entityClub_0, entityMember_5, clubGradeMember);
        ClubMember entityClubMember_6 = Mapping_Member_Club(entityClub_0, entityMember_6, clubGradeMember);
        ClubMember entityClubMember_7 = Mapping_Member_Club(entityClub_0, entityMember_7, clubGradeMember);
        ClubMember entityClubMember_8 = Mapping_Member_Club(entityClub_0, entityMember_8, clubGradeMember);
        ClubMember entityClubMember_9 = Mapping_Member_Club(entityClub_0, entityMember_9, clubGradeDormant);
        ClubMember entityClubMember_10 = Mapping_Member_Club(entityClub_0, entityMember_10, clubGradeDormant);
        ClubMember entityClubMember_11 = Mapping_Member_Club(entityClub_0, entityMember_11, clubGradeDormant);
        ClubMember entityClubMember_12 = Mapping_Member_Club(entityClub_0, entityMember_12, clubGradeDormant);
        // ========================================= Member와 Club의 관계 설정 완료 ========================

        // ========================================= ClubArticle 등록 시작 ========================
        ClubArticle entityClubArticle_suggestion_0 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 0);
        ClubArticle entityClubArticle_suggestion_1 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 1);
        ClubArticle entityClubArticle_suggestion_2 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 2);
        ClubArticle entityClubArticle_suggestion_3 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 3);
        ClubArticle entityClubArticle_freedom_4 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.FREEDOM, 4);
        ClubArticle entityClubArticle_freedom_5 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.FREEDOM, 5);
        ClubArticle entityClubArticle_freedom_6 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.FREEDOM, 6);
        ClubArticle entityClubArticle_freedom_7 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.FREEDOM, 7);
        ClubArticle entityClubArticle_confidential_8 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL, 8);
        ClubArticle entityClubArticle_confidential_9 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL, 9);
        ClubArticle entityClubArticle_confidential_10 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL, 10);
        ClubArticle entityClubArticle_confidential_11 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL, 11);
        ClubArticle entityClubArticle_suggestion_12 = applyClubArticle(CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, 12);
        // ========================================= ClubArticle 등록 완료 ========================


        // ========================================= ClubArticleComment 등록 시작 ========================
        entityClubArticle_suggestion_0 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_0, 0);
        entityClubArticle_suggestion_0 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_0, 1);
        entityClubArticle_suggestion_0 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_0, 2);

        entityClubArticle_suggestion_1 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_1, 0);
        entityClubArticle_suggestion_1 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_1, 1);
        entityClubArticle_suggestion_1 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_1, 2);

        entityClubArticle_suggestion_2 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_2, 0);
        entityClubArticle_suggestion_2 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_2, 1);
        entityClubArticle_suggestion_2 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_2, 2);

        entityClubArticle_suggestion_3 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_3, 0);
        entityClubArticle_suggestion_3 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_3, 1);
        entityClubArticle_suggestion_3 = applyClubArticleCommentToClubArticle(entityClubArticle_suggestion_3, 2);

        entityClubArticle_freedom_4 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_4, 0);
        entityClubArticle_freedom_4 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_4, 1);
        entityClubArticle_freedom_4 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_4, 2);

        entityClubArticle_freedom_5 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_5, 0);
        entityClubArticle_freedom_5 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_5, 1);
        entityClubArticle_freedom_5 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_5, 2);


        entityClubArticle_freedom_6 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_6, 0);
        entityClubArticle_freedom_6 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_6, 1);
        entityClubArticle_freedom_6 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_6, 2);

        entityClubArticle_freedom_7 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_7, 0);
        entityClubArticle_freedom_7 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_7, 1);
        entityClubArticle_freedom_7 = applyClubArticleCommentToClubArticle(entityClubArticle_freedom_7, 2);

        entityClubArticle_confidential_8 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_8, 0);
        entityClubArticle_confidential_8 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_8, 1);
        entityClubArticle_confidential_8 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_8, 2);

        entityClubArticle_confidential_9 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_9, 0);
        entityClubArticle_confidential_9 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_9, 1);
        entityClubArticle_confidential_9 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_9, 2);

        entityClubArticle_confidential_10 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_10, 0);
        entityClubArticle_confidential_10 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_10, 1);
        entityClubArticle_confidential_10 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_10, 2);

        entityClubArticle_confidential_11 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_11, 0);
        entityClubArticle_confidential_11 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_11, 1);
        entityClubArticle_confidential_11 = applyClubArticleCommentToClubArticle(entityClubArticle_confidential_11, 2);
        // ========================================= ClubArticleComment 등록 완료 ========================

        // ========================================= Member와 Club와 ClubArticle의 관계 설정 시작 ========================
//        entityClubMember_0 = Mapping_Member_Club(entityClubMember_0, entityClubArticle_suggestion_0);
//        entityClubMember_1 = Mapping_Member_Club(entityClubMember_1, entityClubArticle_suggestion_1);
//        entityClubMember_2 = Mapping_Member_Club(entityClubMember_2, entityClubArticle_suggestion_2);
//        entityClubMember_3 = Mapping_Member_Club(entityClubMember_3, entityClubArticle_suggestion_3);
//        entityClubMember_4 = Mapping_Member_Club(entityClubMember_4, entityClubArticle_freedom_4);
//        entityClubMember_5 = Mapping_Member_Club(entityClubMember_5, entityClubArticle_freedom_5);
//        entityClubMember_6 = Mapping_Member_Club(entityClubMember_6, entityClubArticle_freedom_6);
//        entityClubMember_7 = Mapping_Member_Club(entityClubMember_7, entityClubArticle_freedom_7);
//        entityClubMember_8 = Mapping_Member_Club(entityClubMember_8, entityClubArticle_confidential_8);
//        entityClubMember_9 = Mapping_Member_Club(entityClubMember_9, entityClubArticle_confidential_9);
//        entityClubMember_10 = Mapping_Member_Club(entityClubMember_10, entityClubArticle_confidential_10);
//        entityClubMember_11 = Mapping_Member_Club(entityClubMember_11, entityClubArticle_confidential_11);
//        entityClubMember_12 = Mapping_Member_Club(entityClubMember_12, entityClubArticle_suggestion_12);
        // ========================================= Member와 Club와 ClubArticle의 관계 설정 완료 ========================

        // ==================================  ClubMember - AttendanceState 매핑 시작 ============================
        entityClubMember_0 = mapping_AttendanceState_ClubMember(entityClubMember_0, attendanceSate_0);
        entityClubMember_1 = mapping_AttendanceState_ClubMember(entityClubMember_1, attendanceSate_1);
        entityClubMember_2 = mapping_AttendanceState_ClubMember(entityClubMember_2, attendanceSate_2);
        entityClubMember_3 = mapping_AttendanceState_ClubMember(entityClubMember_3, attendanceSate_3);
        entityClubMember_4 = mapping_AttendanceState_ClubMember(entityClubMember_4, attendanceSate_4);
        entityClubMember_5 = mapping_AttendanceState_ClubMember(entityClubMember_5, attendanceSate_5);
        entityClubMember_6 = mapping_AttendanceState_ClubMember(entityClubMember_6, attendanceSate_6);
        entityClubMember_7 = mapping_AttendanceState_ClubMember(entityClubMember_7, attendanceSate_7);
        entityClubMember_8 = mapping_AttendanceState_ClubMember(entityClubMember_8, attendanceSate_8);
        entityClubMember_9 = mapping_AttendanceState_ClubMember(entityClubMember_9, attendanceSate_9);
        entityClubMember_10 = mapping_AttendanceState_ClubMember(entityClubMember_10, attendanceSate_10);
        entityClubMember_11 = mapping_AttendanceState_ClubMember(entityClubMember_11, attendanceSate_11);
        entityClubMember_12 = mapping_AttendanceState_ClubMember(entityClubMember_12, attendanceSate_12);
//        // ==================================  ClubMember - AttendanceState 매핑 완료 ============================
//
//        // ==================================  ClubMember - ClubMemberInformation 매핑 시작 ============================
//        entityClubMember_0 = mapping_ClubMemberInformation_ClubMember(entityClubMember_0, 0);
//        entityClubMember_1 = mapping_ClubMemberInformation_ClubMember(entityClubMember_1, 1);
//        entityClubMember_2 = mapping_ClubMemberInformation_ClubMember(entityClubMember_2, 2);
//        entityClubMember_3 = mapping_ClubMemberInformation_ClubMember(entityClubMember_3, 3);
//        entityClubMember_4 = mapping_ClubMemberInformation_ClubMember(entityClubMember_4, 4);
//        entityClubMember_5 = mapping_ClubMemberInformation_ClubMember(entityClubMember_5, 5);
//        entityClubMember_6 = mapping_ClubMemberInformation_ClubMember(entityClubMember_6, 6);
//        entityClubMember_7 = mapping_ClubMemberInformation_ClubMember(entityClubMember_7, 7);
//        entityClubMember_8 = mapping_ClubMemberInformation_ClubMember(entityClubMember_8, 8);
//        entityClubMember_9 = mapping_ClubMemberInformation_ClubMember(entityClubMember_9, 9);
//        entityClubMember_10 = mapping_ClubMemberInformation_ClubMember(entityClubMember_10, 10);
//        entityClubMember_11 = mapping_ClubMemberInformation_ClubMember(entityClubMember_11, 11);
//        entityClubMember_12 = mapping_ClubMemberInformation_ClubMember(entityClubMember_12, 12);
//        // ==================================  ClubMember - AttendanceState 매핑 완료 ============================
    }

    private ClubMember mapping_ClubMemberInformation_ClubMember(ClubMember entityClubMember, Integer number) {
        ClubMemberInformation clubMemberInformation = TestMakeEntity.createSampleClubMemberInformation(number);
        entityClubMember.setClubMemberInformation(clubMemberInformation);
        return clubMemberRepository.save(entityClubMember);
    }

    private ClubMember mapping_AttendanceState_ClubMember(ClubMember entityClubMember, AttendanceSate attendanceSate) {
        entityClubMember.setAttendanceSate(attendanceSate);

        return clubMemberRepository.save(entityClubMember);
    }

    private AttendanceSate mapping_AttendanceSate_AttendanceCheckTime_AttendanceWeekDate_VacationToken() {
        AttendanceSate attendanceSate = new AttendanceSate();
        AttendanceCheckTime attendanceCheckTime = new AttendanceCheckTime();
        AttendanceWeekDate attendanceWeekDate = new AttendanceWeekDate();
        VacationToken vacationToken = new VacationToken();

        attendanceSate.setAttendanceCheckTime(attendanceCheckTime);
        attendanceSate.setAttendanceWeekDate(attendanceWeekDate);
        attendanceSate.setVacationToken(vacationToken);

        return attendanceSateRepository.save(attendanceSate);
    }

    private Club mappring_Club_ClubControl(Club entityClub_0, ClubControl clubControl_0) {
        entityClub_0.setClubControl(clubControl_0);
        entityClub_0 = clubRepository.save(entityClub_0);
        return entityClub_0;
    }

    private ClubControl mapping_ClubControl_VacattionTokenControl_AttendanceWeek() {
        ClubControl clubControl_0 = new ClubControl();
        VacationTokenControl vacationTokenControl_0 = new VacationTokenControl();
        AttendanceWeek attendanceWeek_0 = new AttendanceWeek();

        clubControl_0.setVacationTokenControl(vacationTokenControl_0);
        clubControl_0.setAttendanceWeek(attendanceWeek_0);

        return clubControlRepository.save(clubControl_0);
    }

    private void applyAttendanceNumber_mapping_Club_AttendanceNumber(Club entityClub_0, AttendanceNumber attendanceNumber) {
        AttendanceNumber attendanceNumber_0 = attendanceNumber;
        attendanceNumber_0.setClub(entityClub_0);
        attendanceNumberRepository.save(attendanceNumber_0);
    }

    private ClubMember Mapping_Member_Club(ClubMember entityClubMember, ClubArticle entityClubArticleSuggestion) {
        entityClubMember.addClubArticle(entityClubArticleSuggestion);

        clubArticleRepository.save(entityClubArticleSuggestion);
        return clubMemberRepository.save(entityClubMember);
    }

    private ClubMember Mapping_Member_Club(Club entityClub_0, Member entityMember_0, ClubGrade clubGrade) {
        // ClubMember
        ClubMember clubMember = new ClubMember();

        // Member와 ClubMember의 관계 설정
        clubMember.setMember(entityMember_0);
        clubMember.setClub(entityClub_0);
        clubMember.setClubGrade(clubGrade);

        // Member 엔티티를 다시 저장하여 영속 상태를 유지
        return clubMemberRepository.save(clubMember);
    }

    private Member applyMember(Integer number) {
        Member member = TestMakeEntity.createSampleMember(number);
        Member entityMember = memberRepository.save(member);
        return entityMember;
    }

    private Club applyClub(Integer number) {
        // Club
        Club club = TestMakeEntity.createSampleClub(number);

        // ClubIntroduceImage
        ClubIntroduceImage clubIntroduceImage_0 = TestMakeEntity.createSampleClubIntroduceImage(0);
        ClubIntroduceImage clubIntroduceImage_1 = TestMakeEntity.createSampleClubIntroduceImage(1);
        ClubIntroduceImage clubIntroduceImage_2 = TestMakeEntity.createSampleClubIntroduceImage(2);

        // Club - ClubIntroduceImage의 관계 설정
        club.addClubIntroduceImage(clubIntroduceImage_0);
        club.addClubIntroduceImage(clubIntroduceImage_1);
        club.addClubIntroduceImage(clubIntroduceImage_2);

        Club entityClub = clubRepository.save(club);
        return entityClub;
    }

    private ClubArticle applyClubArticle(CLUB_ARTICLE_CLASSIFICATION clubArticleClassification, Integer number) {
        // ClubArticle
        ClubArticle clubArticle = TestMakeEntity.createSampleClubArticle(clubArticleClassification, number);

        return clubArticleRepository.save(clubArticle);
    }

    private ClubArticle applyClubArticleCommentToClubArticle(ClubArticle entityClubArticle, Integer number) {
        // ClubArticleComment
        ClubArticleComment clubArticleComment = TestMakeEntity.createSampleClubArticleComment(number);

        // ClubArticle - ClubArticleComment
        entityClubArticle.addClubArticleComment(clubArticleComment);
        return clubArticleRepository.save(entityClubArticle);
    }

}