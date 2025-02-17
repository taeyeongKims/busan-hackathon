package hackathon.busan.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadProfileRequest(
        @NotNull Long userId,
        @NotNull MultipartFile multipartFile) {
}