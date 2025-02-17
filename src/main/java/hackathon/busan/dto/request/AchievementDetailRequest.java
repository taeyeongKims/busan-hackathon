package hackathon.busan.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class AchievementDetailRequest {
    private Long missionId;
    private Long userId;
    private String content;
    private List<MultipartFile> images; // multipartFormData 받을 수 있음

    public AchievementDetailRequest() {}

    public AchievementDetailRequest(Long missionId, Long userId, String content, List<MultipartFile> images) {
        this.missionId = missionId;
        this.userId = userId;
        this.content = content;
        this.images = images;
    }
}
