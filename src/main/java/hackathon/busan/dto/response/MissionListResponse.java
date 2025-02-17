package hackathon.busan.dto.response;

import java.util.List;

public record MissionListResponse(
        List<MissionInfoResponse> missionList
) {
}
