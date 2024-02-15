package back.springbootdeveloper.seungchan.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vacation_token_control")
public class VacationTokenControl extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_token_control_id")
    private Long vacationTokenControlId;

    @Column(name = "like_count")
    private Integer vacationTokenControl = 5;

    @Builder
    public VacationTokenControl(Integer vacationTokenControl) {
        this.vacationTokenControl = vacationTokenControl;
    }

    public void updateVacationTokenControl(Integer vacationTokenControl) {
        this.vacationTokenControl = vacationTokenControl;
    }
}
