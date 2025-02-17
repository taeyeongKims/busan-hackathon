package hackathon.busan.dto.request;

import hackathon.busan.entity.Location;

public record AccountRegisterRequest (
        String loginId,
        String password,
        String nickname,
        String image,
        Location location
){
}
