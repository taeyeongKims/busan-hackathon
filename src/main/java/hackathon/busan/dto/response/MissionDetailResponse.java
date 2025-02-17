package hackathon.busan.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record MissionDetailResponse(
        Long missionId,
        Long userId,
        String nickname,

        String title,
        String content,

        Long applyNumber,
        String date,
        Long acheivementNumber,

        List<String>categoryList,
        List<String> imageList
) {
}
