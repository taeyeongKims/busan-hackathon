package hackathon.busan.dto.s3Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record S3UploadRequest(
        @NotNull MultipartFile multipartFile,
        @NotBlank String key) {
}
