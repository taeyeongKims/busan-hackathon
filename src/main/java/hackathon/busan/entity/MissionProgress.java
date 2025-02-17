package hackathon.busan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class MissionProgress extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Account account;
    @OneToOne(fetch = LAZY)
    @JoinColumn
    private Mission mission;

    @Enumerated(EnumType.STRING)
    private MissionStatus status = MissionStatus.IN_PROGRESS;

    public void setStatusCompleted() {
        this.status = MissionStatus.COMPLETED;
    }

    public MissionProgress(Account account, Mission mission) {
        this.account = account;
        this.mission = mission;
    }
}
