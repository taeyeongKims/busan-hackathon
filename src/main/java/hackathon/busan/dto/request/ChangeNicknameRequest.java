package hackathon.busan.dto.request;

public record ChangeNicknameRequest(
        Long userId,
        String nickname
) {
}
