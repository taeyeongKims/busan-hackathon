package hackathon.busan.dto.s3Dto;

import jakarta.validation.constraints.NotBlank;

public record OldKeyRequest(@NotBlank String oldKey) {
}
