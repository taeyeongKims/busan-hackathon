package hackathon.busan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn
    private Location location;
    private String loginId;
    private String password;
    private String nickname;
    private Long missionCount;
    private Long achievementCount;
    private Long point;
    private String profile;

    public Account(Location location, String loginId, String password, String nickname) {
        this.location = location;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.missionCount = Long.valueOf(0);
        this.achievementCount = Long.valueOf(0);
        this.point = Long.valueOf(0);
    }

    public void setMissionCount(Long missionCount) {
        this.missionCount = missionCount;
    }

    public void setAchievementCount(Long achievementCount) {
        this.achievementCount = achievementCount;
    }

    public void setPoint(Long point) {
        this.point = point;
    }
}
