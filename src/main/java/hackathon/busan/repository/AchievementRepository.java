package hackathon.busan.repository;

import hackathon.busan.entity.Achievement;
import hackathon.busan.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Long countByMission(Mission mission);

    Optional<Achievement> findByAccountIdAndMissionId(Long userId, Long missionId);

//    @Query("select new hackathon.busan.dto.response.AchievementDetailResponse(" +
//            "ac.id, ac.account.id, ac.mission.id, ac.content, ) " +
//            " from Achievement ac where ac.id =:id"
//    )
//    void findAchievementDetailResponseById(Long id);


    List<Achievement> findByMission(Mission mission);

}
