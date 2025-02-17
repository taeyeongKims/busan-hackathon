package hackathon.busan.dto.response;

import java.time.LocalDateTime;

public record MissionInfoResponse(
        Long missionId,
        Long userId,
        String nickname,
        String title,
        String image,
        Long scrapNumber,
        //Long applyNumber,
        Long acheivementNumber,
        String date
) {
}
