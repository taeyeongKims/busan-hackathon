package hackathon.busan.dto.response;

public record RankingUserInfoResponse(
        String profile,
        String name,
        Long missionCount,
        Long achievementCount,
        Long sum
) {
}
