package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.AttendanceSateRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
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

    @Autowired
    public EntityUtilService(ClubRepository clubRepository, AttendanceSateRepository attendanceSateRepository, ClubMemberRepository clubMemberRepository) {
        this.clubRepository = clubRepository;
        this.attendanceSateRepository = attendanceSateRepository;
        this.clubMemberRepository = clubMemberRepository;
    }

    private static Club createClub(String clubName, String clubIntroduce, String clubProfileImage) {
        return Club.builder()
                .clubName(clubName)
                .clubIntroduce(clubIntroduce)
                .clubProfileImage(clubProfileImage)
                .build();
    }

    private static ClubMember creatClubMember(Member member, Club club, ClubMemberInformation clubMemberInformation, AttendanceSate attendanceSate, ClubGrade clubGrade) {
        return ClubMember.builder()
                .memberId(member.getMemberId())
                .clubMemberInformationId(clubMemberInformation.getClubMemberInformationId())
                .clubId(club.getClubId())
                .clubGradeId(clubGrade.getClubGradeId())
                .attendanceSateId(attendanceSate.getAttendanceStateId())
                .clubMemberInformationId(clubMemberInformation.getClubMemberInformationId())
                .build();
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
        ClubGrade clubGrade = new ClubGrade(CLUB_GRADE);

        // ClubMember
        //   - Member, Club, ClubMemberInformation, AttendanceSate, ClubGrade
        ClubMember clubMember = creatClubMember(member, club, clubMemberInformation, attendanceSate, clubGrade);

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
}
