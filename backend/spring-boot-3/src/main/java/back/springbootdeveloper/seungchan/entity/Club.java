package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club")
public class Club {
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

    @Builder
    public Club(String group) {
        this.clubName = group;
    }

    public void updateGroup(String group) {
        this.clubName = group;
    }
}
