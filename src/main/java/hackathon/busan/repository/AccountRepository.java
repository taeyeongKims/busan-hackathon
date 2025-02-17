package hackathon.busan.repository;

import hackathon.busan.entity.Account;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a.profile FROM Account a WHERE a.id = :accountId")
    Optional<String> findProfileImageByAccountId(@Param("accountId") Long accountId);

    @Modifying
    @Query("update Account a set a.profile = :profileImage where a.id = :accountId")
    void updateProfileImageByAccountId(@Param("profileImage") String profileImage, @Param("accountId") Long accountId);
}
