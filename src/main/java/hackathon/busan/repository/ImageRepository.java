package hackathon.busan.repository;

import hackathon.busan.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i.url FROM Image i WHERE i.account.id = :accountId AND i.mission.id =:missionId")
    List<String> findUrlsByAccountIdAndMissionId(Long accountId, Long missionId);

}
