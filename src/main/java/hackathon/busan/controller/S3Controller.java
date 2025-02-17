package hackathon.busan.controller;

import hackathon.busan.dto.request.UploadProfileRequest;
import hackathon.busan.dto.response.UploadProfileResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class S3Controller {

    @PostMapping(value = "/upload/profile/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadProfileResponse> upload(
            @PathVariable final Long userId,
            @RequestParam("file") final MultipartFile file) {
        UploadProfileRequest request = new UploadProfileRequest(userId, file);
        return ResponseEntity.ok(new UploadProfileResponse(null));
    }
}
