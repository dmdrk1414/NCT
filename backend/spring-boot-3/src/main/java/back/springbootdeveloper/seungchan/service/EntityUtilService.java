package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceNumber;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubIntroduceImage;
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
    public Club applyClub(String clubName, String clubIntroduce, String clubProfileImage, List<ClubIntroduceImage> clubIntroduceImages) {
        final Club club = Club.builder()
                .clubName(clubName)
                .clubIntroduce(clubIntroduce)
                .clubProfileImage(clubProfileImage)
                .build();

        club.addAttendanceNumber(new AttendanceNumber());
        clubIntroduceImages.forEach(clubIntroduceImage
                -> club.addClubIntroduceImage(clubIntroduceImage));

        return clubRepository.save(club);
    }
}
