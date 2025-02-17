package hackathon.busan.repository;

import hackathon.busan.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);

    Account findByLoginIdAndPassword(String loginId, String password);

    @Query("SELECT a.profile FROM Account a WHERE a.id = :accountId")
    Optional<String> findProfileImageByAccountId(@Param("accountId") Long accountId);

    @Modifying
    @Query("update Account a set a.profile = :profileImage where a.id = :accountId")
    void updateProfileImageByAccountId(@Param("profileImage") String profileImage, @Param("accountId") Long accountId);
}
