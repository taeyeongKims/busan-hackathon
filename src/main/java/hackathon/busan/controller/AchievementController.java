package hackathon.busan.controller;

import hackathon.busan.dto.request.AchievementDetailRequest;
import hackathon.busan.dto.request.ScrapAchievementRequest;
import hackathon.busan.dto.response.AchievementDetailResponse;
import hackathon.busan.dto.response.ScrapAchievementListResponse;
import hackathon.busan.service.AchievementService;
import hackathon.busan.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;
    private final MissionService missionService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AchievementDetailResponse> postAchievement(
            @ModelAttribute final AchievementDetailRequest achievementDetailRequest
    ) {
        return ResponseEntity.ok(achievementService.postAchievement(achievementDetailRequest));
    }

    @PostMapping("/scrap")
    public ResponseEntity<String> likeAchievement(
            @RequestBody final ScrapAchievementRequest scrapAchievementRequest
    ) {
        achievementService.likeAchievement(scrapAchievementRequest);
        return ResponseEntity.ok("좋아요를 성공하였습니다.");
    }

    @PostMapping("/scrap/cancel")
    public ResponseEntity<String> cancelLikeAchievement(
            @RequestBody final ScrapAchievementRequest scrapAchievementRequest
    ) {
        achievementService.cancelLikeAchievement(scrapAchievementRequest);
        return ResponseEntity.ok("좋아요를 취소하였습니다.");
    }

    @GetMapping("/scrap/{userId}")
    public ResponseEntity<ScrapAchievementListResponse> getLikeAchievement(
            @PathVariable("userId") final Long userId
    ) {
        return ResponseEntity.ok(achievementService.getLikeAchievement(userId));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> getAchievementList(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(missionService.getAchievementList(userId));
    }
}
