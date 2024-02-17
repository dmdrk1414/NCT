package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EntityUtilService {
    private final ClubRepository clubRepository;

    @Autowired
    public EntityUtilService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    private static Club createClub(String clubName, String clubIntroduce, String clubProfileImage) {
        return Club.builder()
                .clubName(clubName)
                .clubIntroduce(clubIntroduce)
                .clubProfileImage(clubProfileImage)
                .build();
    }

    /**
     * Club을 생성하고 저장합니다.
     *
     * @param clubName            Club의 이름
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
     * ClubControl 생성
     *
     * @return
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
