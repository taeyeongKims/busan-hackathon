package hackathon.busan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Achievement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Account account;
    @OneToOne(fetch = LAZY)
    @JoinColumn
    private Mission mission;

    private String content;
    private Long likeCount = 0L;

    public Achievement(Account user, Mission mission, String content) {
        this.account = user;
        this.mission = mission;
        this.content = content;
    }
}
