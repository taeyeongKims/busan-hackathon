package hackathon.busan.repository;

import hackathon.busan.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);

    Account findByLoginIdAndPassword(String loginId, String password);
}
