package hackathon.busan.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record MissionDetailResponse(
        Long missionId,
        Long userId,
        String nickname,

        String title,
        String content,

        Integer applyNumber,
        LocalDateTime date,
        Integer acheivementNumber,

        List<String>categoryList,
        List<String> imageList
) {
}
