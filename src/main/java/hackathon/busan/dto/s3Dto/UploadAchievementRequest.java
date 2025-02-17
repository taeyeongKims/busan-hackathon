package hackathon.busan.dto.s3Dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UploadAchievementRequest(
        List<MultipartFile> images,
        Long userId,
        Long missionId
) {
}
