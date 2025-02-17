package hackathon.busan.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record AchievementDetailResponse(
        Long id,
        Long accountId,
        Long missionId,
        String title,
        String content,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}