package hackathon.busan.controller;

import hackathon.busan.dto.response.RankingListResponse;
import hackathon.busan.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity<RankingListResponse> getRanking() {
        return ResponseEntity.ok(rankingService.getRanking());
    }
}
