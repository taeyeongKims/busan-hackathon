package hackathon.busan.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hackathon.busan.entity.Location;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MissionDetailRequest {
    private Long userId;
    private String title;
    private String content;
    private List<String> categories; // multipartFormData 받을 수 있음
    private List<MultipartFile> images; // multipartFormData 받을 수 있음
    // Location 필드를 개별 필드로 분리
    private String zipcode;
    private String address;
    private String detailAddress;
    private String sido;
    private String sigugun;
    private String dong;

    // Location 객체로 변환하는 메서드
    @JsonIgnore
    public Location getLocationAsObject() {
        return new Location(zipcode, address, detailAddress, sido, sigugun, dong);
    }

    public MissionDetailRequest() {}
}