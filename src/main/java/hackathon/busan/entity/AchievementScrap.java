package hackathon.busan.entity;

import jakarta.persistence.*;

public class AchievementScrap {
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
