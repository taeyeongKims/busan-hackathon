package hackathon.busan.controller;

import hackathon.busan.dto.request.AchievementDetailRequest;
import hackathon.busan.dto.request.ScrapAchievementRequest;
import hackathon.busan.dto.response.AchievementDetailResponse;
import hackathon.busan.dto.response.ScrapAchievementListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievement")
public class AchievementController {
    @GetMapping("/{missionId}")
    public ResponseEntity<AchievementDetailResponse> getAchievement(
            @PathVariable final Long missionId
    ) {
        return ResponseEntity.ok(new AchievementDetailResponse(null, null, null, null, null,null, null, null));
    }

    @PostMapping()
    public ResponseEntity<AchievementDetailResponse> postAchievement(
            @RequestBody final AchievementDetailRequest achievementDetailRequest
    ) {
        return ResponseEntity.ok(new AchievementDetailResponse(null, null, null, null, null,null, null, null));
    }

    @PostMapping("/scrap")
    public ResponseEntity<String> likeAchievement(
            @RequestBody final ScrapAchievementRequest scrapAchievementRequest
    ) {
        return ResponseEntity.ok("좋아요를 성공하였습니다.");
    }

    @PostMapping("/scrap/cancel")
    public ResponseEntity<String> cancelLikeAchievement(
            @RequestBody final ScrapAchievementRequest scrapAchievementRequest
    ) {
        return ResponseEntity.ok("좋아요를 취소하였습니다.");
    }

    @GetMapping("/scrap/{userId}")
    public ResponseEntity<ScrapAchievementListResponse> getLikeAchievement(
            @PathVariable final Long userId
    ) {
        AchievementDetailResponse response = new AchievementDetailResponse(null, null, null, null, null, null, null, null);
        List<AchievementDetailResponse> result = List.of(response);
        return ResponseEntity.ok(new ScrapAchievementListResponse(result));
    }
}
