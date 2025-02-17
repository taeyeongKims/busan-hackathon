package hackathon.busan.dto.response;

import hackathon.busan.entity.Location;

public record AccountUserInfoResponse(
        Long userId,
        String nickname,
        /*String zipcode,
        String address,
        String detailAddress,
        String sido,
        String sigugun,
        String dong*/
        Location location
) {
}
