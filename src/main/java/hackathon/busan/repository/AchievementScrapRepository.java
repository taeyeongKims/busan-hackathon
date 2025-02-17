package hackathon.busan.repository;

import hackathon.busan.entity.AchievementScrap;
import jakarta.persistence.SequenceGenerators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AchievementScrapRepository extends JpaRepository<AchievementScrap, Long> {
    Optional<AchievementScrap> findByAccountIdAndAchievementId(Long accountId, Long achievementId);

    Long countById(Long id);
}