package hackathon.busan.repository;

import hackathon.busan.entity.MissionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionCategoryRepository extends JpaRepository<MissionCategory, Long> {
}
