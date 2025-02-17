package hackathon.busan.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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
}
