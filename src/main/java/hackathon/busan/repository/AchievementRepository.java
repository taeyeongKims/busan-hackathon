package hackathon.busan.repository;

import hackathon.busan.entity.Achievement;
import hackathon.busan.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Long countByMission(Mission mission);
}
