package hackathon.busan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AchievementScrap extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Achievement achievement;

    public AchievementScrap(Account user, Achievement achievement) {
        this.account = user;
        this.achievement = achievement;
    }
}
