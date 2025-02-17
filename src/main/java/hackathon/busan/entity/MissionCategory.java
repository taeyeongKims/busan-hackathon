package hackathon.busan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class MissionCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Mission mission;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Category category;

    public MissionCategory(Mission mission, Category category) {
        this.mission = mission;
        this.category = category;
    }
}
