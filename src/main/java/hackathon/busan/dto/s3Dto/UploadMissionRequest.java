package hackathon.busan.dto.s3Dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UploadMissionRequest(
        Long userId,
        Long missionId,
        List<MultipartFile> images
) {
}
