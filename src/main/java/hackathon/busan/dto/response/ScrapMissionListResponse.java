package hackathon.busan.dto.response;

import java.util.List;

public record ScrapMissionListResponse(
        List<MissionInfoResponse> scrapMissionList
) {
}
