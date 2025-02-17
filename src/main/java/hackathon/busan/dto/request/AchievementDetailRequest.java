package hackathon.busan.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AchievementDetailRequest(
        Long missionId,
        Long userId,
        String title,
        String content,
        List<MultipartFile> images
) {
}
