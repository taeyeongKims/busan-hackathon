package hackathon.busan.controller;

import hackathon.busan.dto.request.MissionDetailRequest;
import hackathon.busan.dto.request.MissionInfoRequest;
import hackathon.busan.dto.request.MissionUserInfoRequest;
import hackathon.busan.dto.response.AchievementDetailResponse;
import hackathon.busan.dto.response.AchievementListResponse;
import hackathon.busan.dto.response.MissionDetailResponse;
import hackathon.busan.dto.response.MissionListResponse;
import hackathon.busan.entity.Location;
import hackathon.busan.service.AchievementService;
import hackathon.busan.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mission")
public class MissionController {

    private final MissionService missionService;
    private final AchievementService achievementService;

    @Operation(summary = "미션 리스트 조회")
    @GetMapping()
    public ResponseEntity<MissionListResponse> getMissionList(@RequestParam(value = "filter", required = false) String filter,
                                                              @RequestParam(value = "criteria", required = false) List<String> criteria) {
        return ResponseEntity.ok(missionService.getMissionList(filter, criteria));
    }

    @Operation(summary = "미션 생성")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MissionDetailResponse> createMission(@ModelAttribute MissionDetailRequest request) {
        Location location = request.getLocationAsObject();
        MissionDetailResponse result = missionService.createMission(new MissionInfoRequest(
                request.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getCategories(),
                request.getImages(),
                location));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "미션 상세 조회")
    @GetMapping("/{missionId}")
    public ResponseEntity<MissionDetailResponse> getDetailMission(@PathVariable Long missionId) {
        return ResponseEntity.ok(missionService.detailMission(missionId));
    }

    @Operation(summary = "미션 인증 리스트 조회")
    @GetMapping("/{missionId}/achievement")
    public ResponseEntity<AchievementListResponse> getAchievementList(@PathVariable Long missionId) {
        return ResponseEntity.ok(achievementService.getAchievementList(missionId));
    }

    @Operation(summary = "미션 찜하기")
    @PostMapping("/scrap")
    public ResponseEntity<?> scrapMission(@RequestBody MissionUserInfoRequest request) {
        missionService.scrapMission(request);
        return ResponseEntity.ok("해당 미션을 찜하였습니다.");
    }

    @Operation(summary = "미션 찜 취소하기")
    @PostMapping("/scrap/cancel")
    public ResponseEntity<?> cancelScrapMission(@RequestBody MissionUserInfoRequest request) {
        missionService.cancelScrapMission(request);
        return ResponseEntity.ok("해당 미션의 찜을 취소하였습니다.");
    }

    @Operation(summary = "미션 참여 신청하기")
    @PostMapping("/apply")
    public ResponseEntity<?> applyMission(@RequestBody MissionUserInfoRequest request) {
        missionService.applyMission(request);
        return ResponseEntity.ok("미션 참여 신청을 완료하였습니다.");
    }

    @Operation(summary = "미션 찜 리스트 조회")
    @GetMapping("/apply/{userId}")
    public ResponseEntity<?> applyMissionList(@PathVariable Long userId) {
        return ResponseEntity.ok(missionService.getScrapMissionList(userId));
    }
}
