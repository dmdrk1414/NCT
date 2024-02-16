package back.springbootdeveloper.seungchan.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_member")
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_member_id")
    private Long clubMemberId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "club_grade_id")
    private Long clubGradeId;

    @Column(name = "attendance_state_id")
    private Long attendanceSateId;

    @Column(name = "club_member_information_id")
    private Long clubMemberInformationId;
}
