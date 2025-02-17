package hackathon.busan.dto.request;

import hackathon.busan.entity.Location;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record MissionInfoRequest(
        Long userId,
        String title,
        String content,
        List<String>category,
        List<MultipartFile> image,
        Location location
) {
}
