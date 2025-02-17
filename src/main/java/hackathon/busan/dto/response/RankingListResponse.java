package hackathon.busan.dto.response;

import java.util.List;

public record RankingListResponse(
    List<RankingUserInfoResponse> ranking
) {
}
