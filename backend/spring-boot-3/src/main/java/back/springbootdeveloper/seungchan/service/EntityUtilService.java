package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EntityUtilService {
    private final ClubRepository clubRepository;
    private final AttendanceSateRepository attendanceSateRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubMemberInformationRepository clubMemberInformationRepository;
    private final ClubGradeRepository clubGradeRepository;

    @Autowired
    public EntityUtilService(ClubRepository clubRepository, AttendanceSateRepository attendanceSateRepository, ClubMemberRepository clubMemberRepository, ClubMemberInformationRepository clubMemberInformationRepository, ClubGradeRepository clubGradeRepository) {
        this.clubRepository = clubRepository;
        this.attendanceSateRepository = attendanceSateRepository;
        this.clubMemberRepository = clubMemberRepository;
        this.clubMemberInformationRepository = clubMemberInformationRepository;
        this.clubGradeRepository = clubGradeRepository;
    }


    /**
     * Club을 생성하고 저장합니다.
     *
     * @param clubName            Club 이름
     * @param clubIntroduce       Club 자기소개
     * @param clubProfileImage    Club 프로필 사진 URL
     * @param clubIntroduceImages Club 자기소개 사진 리스트
     * @return 저장된 Club
     */
    @Transactional
    public Club saveClub(String clubName, String clubIntroduce, String clubProfileImage, List<ClubIntroduceImage> clubIntroduceImages) {
        final Club club = createClub(clubName, clubIntroduce, clubProfileImage);
        final ClubControl clubControl = createClubControl();

        // Club - AttendanceNumber
        club.addAttendanceNumber(new AttendanceNumber());
        // Club - ClubIntroduceImages
        clubIntroduceImages.forEach(clubIntroduceImage -> club.addClubIntroduceImage(clubIntroduceImage));
        // Club - ClubControl
        club.setClubControl(clubControl);

        return clubRepository.save(club);
    }

    /**
     * ClubMember 생성하고 저장합니다.
     *
     * @param member                ClubMember의 회원 정보
     * @param club                  ClubMember
     * @param CLUB_GRADE            ClubMember의 등급
     * @param clubMemberInformation ClubMember의 추가 정보
     * @return 생성된 클럽 멤버
     */
    @Transactional
    public ClubMember applyClub(Member member, Club club, CLUB_GRADE CLUB_GRADE, ClubMemberInformation clubMemberInformation) {
        AttendanceSate attendanceSate = attendanceSateRepository.save(createAttendanceState());
        ClubGrade clubGrade = clubGradeRepository.findByClubGrade(CLUB_GRADE);
        ClubMemberInformation entityClubMemberInformation = clubMemberInformationRepository.save(clubMemberInformation);

        // ClubMember
        //   - Member, Club, ClubMemberInformation, AttendanceSate, ClubGrade
        ClubMember clubMember = creatClubMember(member, club, entityClubMemberInformation, attendanceSate, clubGrade);

        return clubMemberRepository.save(clubMember);
    }

    /**
     * AttendanceState 생성
     *
     * @return AttendanceState 반환
     */
    private AttendanceSate createAttendanceState() {
        AttendanceSate attendanceSate = new AttendanceSate();
        AttendanceCheckTime attendanceCheckTime = new AttendanceCheckTime();
        AttendanceWeekDate attendanceWeekDate = new AttendanceWeekDate();
        VacationToken vacationToken = new VacationToken();

        // attendanceSate - attendanceCheckTime
        attendanceSate.setAttendanceCheckTime(attendanceCheckTime);
        // attendanceSate - attendanceWeekDate
        attendanceSate.setAttendanceWeekDate(attendanceWeekDate);
        // attendanceSate - vacationToken
        attendanceSate.setVacationToken(vacationToken);

        return attendanceSate;
    }

    /**
     * ClubControl 생성
     *
     * @return ClubControl 반환
     */
    private ClubControl createClubControl() {
        ClubControl clubControl = new ClubControl();
        VacationTokenControl vacationTokenControl = new VacationTokenControl();
        AttendanceWeek attendanceWeek = new AttendanceWeek();

        // ClubControl - VacationTokenControl
        clubControl.setVacationTokenControl(vacationTokenControl);
        // ClubControl - AttendanceWeek
        clubControl.setAttendanceWeek(attendanceWeek);

        return clubControl;
    }

    /**
     * 주어진 정보를 사용하여 Club 객체를 생성합니다.
     *
     * @param clubName         Club 이름
     * @param clubIntroduce    Club 자기소개
     * @param clubProfileImage Club 프로필 이미지 URL
     * @return 생성된 Club 객체
     */
    private Club createClub(String clubName, String clubIntroduce, String clubProfileImage) {
        return Club.builder()
                .clubName(clubName)
                .clubIntroduce(clubIntroduce)
                .clubProfileImage(clubProfileImage)
                .build();
    }

    /**
     * 주어진 정보를 사용하여 클럽 ClubMember 객체를 생성합니다.
     *
     * @param member                ClubMember 멤버의 회원 정보
     * @param club                  Club 정보
     * @param clubMemberInformation Club 멤버의 추가 정보
     * @param attendanceSate        Club 멤버의 출석 상태
     * @param clubGrade             Club 멤버의 등급
     * @return 생성된 ClubMember 멤버 객체
     */
    private ClubMember creatClubMember(Member member, Club club, ClubMemberInformation clubMemberInformation, AttendanceSate attendanceSate, ClubGrade clubGrade) {
        return ClubMember.builder()
                .memberId(member.getMemberId())
                .clubMemberInformationId(clubMemberInformation.getClubMemberInformationId())
                .clubId(club.getClubId())
                .clubGradeId(clubGrade.getClubGradeId())
                .attendanceSateId(attendanceSate.getAttendanceStateId())
                .clubMemberInformationId(clubMemberInformation.getClubMemberInformationId())
                .build();
    }
}
