package hackathon.busan.repository;

import hackathon.busan.entity.Account;
import hackathon.busan.entity.Mission;
import hackathon.busan.entity.MissionScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionScrapRepository extends JpaRepository<MissionScrap, Long> {

    MissionScrap findByAccountAndMission(Account account, Mission mission);

    List<MissionScrap> findAllByAccount(Account account);

    Long countByMission(Mission mission);
}
