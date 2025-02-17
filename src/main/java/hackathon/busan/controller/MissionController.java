package hackathon.busan.controller;

import hackathon.busan.dto.request.MissionUserInfoRequest;
import hackathon.busan.dto.response.AchievementDetailResponse;
import hackathon.busan.dto.response.AchievementListResponse;
import hackathon.busan.dto.response.MissionDetailResponse;
import hackathon.busan.dto.response.MissionListResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Operation(summary = "미션 리스트 조회")
    @GetMapping()
    public ResponseEntity<MissionListResponse> getMissionList() {
        return ResponseEntity.ok(new MissionListResponse(null));
    }

    @Operation(summary = "미션 생성")
    @PostMapping("/create")
    public void createMission() {

    }

    @Operation(summary = "미션 상세 조회")
    @GetMapping("/{missionId}")
    public ResponseEntity<MissionDetailResponse> getDetailMission(@PathVariable Long missionId) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "미션 인증 리스트 조회")
    @GetMapping("/{missionId}/achievement")
    public ResponseEntity<AchievementListResponse> getAchievementList(@PathVariable Long missionId) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "미션 찜하기")
    @PostMapping("/scrap")
    public ResponseEntity<?> scrapMission(@RequestBody MissionUserInfoRequest request) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "미션 찜 취소하기")
    @PostMapping("/scrap/cancel")
    public ResponseEntity<?> cancelScrapMission(@RequestBody MissionUserInfoRequest request) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "미션 참여 신청하기")
    @PostMapping("/apply")
    public ResponseEntity<?> applyMission(@RequestBody MissionUserInfoRequest request) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "미션 찜 리스트 조회")
    @GetMapping("/apply/{userId}")
    public ResponseEntity<?> applyMissionList(@PathVariable Long userId) {
        return ResponseEntity.ok(null);
    }
}
