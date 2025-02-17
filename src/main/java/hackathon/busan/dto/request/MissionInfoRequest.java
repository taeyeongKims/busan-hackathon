package hackathon.busan.dto.request;

import java.util.List;

public record MissionInfoRequest(
        Long userId,
        String title,
        String content,
        List<String>category,
        List<String> image,
        String zipcode,
        String address,
        String detailAddress,
        String sido,
        String sigugun,
        String dong
) {
}
