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

    @OneToOne(mappedBy = "club")
    private ClubMember clubMember;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id")
    private List<ClubIntroduceImage> clubIntroduceImages = new ArrayList<>();


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

    public void setClubMember(final ClubMember clubMember) {
        this.clubMember = clubMember;

        if (clubMember.getClub() != this) { // null 체크 추가
            clubMember.setClub(this);
        }
    }

    public void addClubIntroduceImage(final ClubIntroduceImage clubIntroduceImage) {
        this.clubIntroduceImages.add(clubIntroduceImage);
    }
}
