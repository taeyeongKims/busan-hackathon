package hackathon.busan.dto.s3Dto;

import java.util.List;

public record UploadAchievementResponse(
        List<String> images
) {
}
