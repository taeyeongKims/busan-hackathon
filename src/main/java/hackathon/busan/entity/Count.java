package hackathon.busan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Count extends BaseEntity{
    @Id
    private Long userId;
    private Long count;
}
