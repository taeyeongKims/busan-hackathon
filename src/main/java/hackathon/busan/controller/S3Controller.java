package hackathon.busan.controller;

import hackathon.busan.dto.request.UploadProfileRequest;
import hackathon.busan.dto.response.UploadProfileResponse;
import hackathon.busan.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping(value = "/upload/profile/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadProfileResponse> upload(
            @PathVariable("userId") final Long userId,
            @RequestParam("file") final MultipartFile file) {
        UploadProfileRequest request = new UploadProfileRequest(userId, file);
        String profile = s3Service.uploadProfile(request).profile();
        return ResponseEntity.ok(new UploadProfileResponse(profile));
    }
}
