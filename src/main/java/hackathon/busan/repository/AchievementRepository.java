package hackathon.busan.repository;

import hackathon.busan.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    Optional<Achievement> findByAccountIdAndMissionId(Long userId, Long missionId);

//    @Query("select new hackathon.busan.dto.response.AchievementDetailResponse(" +
//            "ac.id, ac.account.id, ac.mission.id, ac.content, ) " +
//            " from Achievement ac where ac.id =:id"
//    )
//    void findAchievementDetailResponseById(Long id);
}
