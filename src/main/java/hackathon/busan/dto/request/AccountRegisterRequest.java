package hackathon.busan.dto.request;

public record AccountRegisterRequest (
        String login,
        String password,
        String image,
        String zipcode,
        String address,
        String detailAddress,
        String sido,
        String sigugun,
        String dong
){
}
