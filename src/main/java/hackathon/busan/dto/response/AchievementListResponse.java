package hackathon.busan.dto.response;

import java.util.List;

public record AchievementListResponse(
        List<AchievementDetailResponse> achieveList
) {
}
