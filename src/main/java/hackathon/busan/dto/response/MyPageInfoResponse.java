package hackathon.busan.dto.response;

public record MyPageInfoResponse(
        String profile,
        String name,
        Long point,
        Long missionCount,
        Long achievementCount
) {
}
