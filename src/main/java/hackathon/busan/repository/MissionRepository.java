package hackathon.busan.repository;

import hackathon.busan.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findAll(); // 모든 미션 조회

    Optional<Mission> findById(Long id);
}
