package hackathon.busan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"createdDate", "updatedDate"})
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String zipcode;
    private String address;
    private String detailAddress;
    private String sido;
    private String sigugun;
    private String dong;

    public Location(String zipcode, String address, String detailAddress, String sido, String sigugun, String dong) {
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.sido = sido;
        this.sigugun = sigugun;
        this.dong = dong;
    }
}