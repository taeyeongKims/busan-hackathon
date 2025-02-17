package hackathon.busan.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record AchievementDetailResponse(
        Long achievementId,
        Long accountId,
        Long missionId,
        String content,
        List<String> images,
        Long likeCount,
        String date
) {
}