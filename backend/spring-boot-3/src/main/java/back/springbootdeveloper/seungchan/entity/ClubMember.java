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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_grade_id")
    private ClubGrade clubGrade;

    @OneToOne(mappedBy = "clubMember", cascade = CascadeType.REMOVE)
    private ClubArticle clubArticle;

    public void setMember(final Member member) {
        this.member = member;

        if (!member.getClubMembers().contains(this)) { // null 체크 추가
            member.getClubMembers().add(this);
        }
    }

    public void setClub(final Club club) {
        this.club = club;

        if (club.getClubMember() != this) { // null 체크 추가
            club.setClubMember(this);
        }
    }

    public void setClubGrade(final ClubGrade clubGrade) {
        this.clubGrade = clubGrade;
    }

    public void setClubArticle(final ClubArticle clubArticle) {
        this.clubArticle = clubArticle;

        if (clubArticle.getClubMember() != this) { // null 체크 추가
            clubArticle.setClubMember(this);
        }
    }
}
