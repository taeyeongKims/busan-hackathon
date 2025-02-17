package hackathon.busan.repository;

import hackathon.busan.entity.Mission;
import hackathon.busan.entity.MissionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MissionProgressRepository extends JpaRepository<MissionProgress, Long> {
    Optional<MissionProgress> findByAccountIdAndMissionId(Long aLong, Long aLong1);

    Long countByMission(Mission mission);
}
