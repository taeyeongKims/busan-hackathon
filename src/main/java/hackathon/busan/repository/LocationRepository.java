package hackathon.busan.repository;

import hackathon.busan.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean existsByZipcode(String zipcode);

    Location findByZipcode(String zipcode);
}
