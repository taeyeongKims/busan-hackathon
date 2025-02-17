package hackathon.busan.controller;

import hackathon.busan.dto.request.ChangeNicknameRequest;
import hackathon.busan.dto.response.MyPageInfoResponse;
import hackathon.busan.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/info/{userId}")
    public ResponseEntity<MyPageInfoResponse> getMyInfo(
            @PathVariable final Long userId
    ) {
        myPageService.getMyInfo(userId);
        return ResponseEntity.ok(myPageService.getMyInfo(userId));
    }

    @PostMapping("/change/nickname")
    public ResponseEntity<MyPageInfoResponse> changeNickname(
            @RequestBody final ChangeNicknameRequest request
    ) {
        return ResponseEntity.ok(myPageService.changeNickname(request));
    }
}
