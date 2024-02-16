package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club")
public class Club extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "club_name", length = 30, nullable = false, unique = true)
    private String clubName;

    @Column(name = "club_introduce", length = 1000, nullable = false)
    private String clubIntroduce;

    @Column(name = "club_profile_image", length = 200, nullable = false)
    private String clubProfileImage;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id")
    private List<ClubIntroduceImage> clubIntroduceImages = new ArrayList<>();

    @OneToOne(mappedBy = "club")
    private AttendanceNumber attendanceNumber;

    @OneToOne
    @JoinColumn(name = "club_control_id")
    private ClubControl clubControl;

    @Builder
    public Club(String clubName, String clubIntroduce, String clubProfileImage) {
        this.clubName = clubName;
        this.clubIntroduce = clubIntroduce;
        this.clubProfileImage = clubProfileImage;
    }

    public void updateClubName(String group) {
        this.clubName = group;
    }

    public void updateClubIntroduce(String clubIntroduce) {
        this.clubIntroduce = clubIntroduce;
    }

    public void updateClubProfileImage(String clubProfileImage) {
        this.clubProfileImage = clubProfileImage;
    }

    public void addClubIntroduceImage(final ClubIntroduceImage clubIntroduceImage) {
        this.clubIntroduceImages.add(clubIntroduceImage);
    }

    public void setAttendanceNumber(final AttendanceNumber attendanceNumber) {
        this.attendanceNumber = attendanceNumber;

        if (attendanceNumber.getClub() != this) { // null 체크 추가
            attendanceNumber.setClub(this);
        }
    }

    public void setClubControl(final ClubControl clubControl) {
        this.clubControl = clubControl;

        if (clubControl.getClub() != this) { // null 체크 추가
            clubControl.setClub(this);
        }
    }
}
