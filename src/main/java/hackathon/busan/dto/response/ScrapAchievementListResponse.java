package hackathon.busan.dto.response;

import java.util.List;

public record ScrapAchievementListResponse(
        List<AchievementDetailResponse> likeAchievements
) {
}
