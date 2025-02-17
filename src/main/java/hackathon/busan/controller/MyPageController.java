package hackathon.busan.controller;

import hackathon.busan.dto.request.ChangeNicknameRequest;
import hackathon.busan.dto.response.MyPageInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
public class MyPageController {
    @GetMapping("/info/{userId}")
    public ResponseEntity<MyPageInfoResponse> getMyInfo(
            @PathVariable final Long userId
    ) {
        return ResponseEntity.ok(new MyPageInfoResponse(null, null, null, null, null));
    }

    @PostMapping("/change/nickname")
    public ResponseEntity<MyPageInfoResponse> changeNickname(
            @RequestBody final ChangeNicknameRequest request
    ) {
        return ResponseEntity.ok(new MyPageInfoResponse(null, null, null, null, null));
    }
}
