package hackathon.busan.repository;

import hackathon.busan.entity.AchievementScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementScrapRepository extends JpaRepository<AchievementScrap, Long> {
    void findByAccountIdAndAchievementId();
}