package hackathon.busan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Account account;

    @OneToMany(fetch = LAZY)
    @JoinColumn
    private List<Category> categories;

    @OneToOne(fetch = LAZY)
    @JoinColumn
    private Location location;

    private String title;
    private String content;
    private Long likeCount = 0L;

    public Mission(Account account, List<Category> categories, Location location, String title, String content) {
        this.account = account;
        this.categories = categories;
        this.location = location;
        this.title = title;
        this.content = content;
    }
}
