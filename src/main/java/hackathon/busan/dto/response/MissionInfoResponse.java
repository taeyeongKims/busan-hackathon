package hackathon.busan.dto.response;

import java.time.LocalDateTime;

public record MissionInfoResponse(
        Long missionId,
        Long userId,
        String nickname,
        String title,
        String image,
        Integer scrapNumber,
        Integer applyNumber,
        Integer acheivementNumber,
        LocalDateTime date
) {
}
