package hackathon.busan.controller;

import hackathon.busan.dto.response.RankingListResponse;
import hackathon.busan.dto.response.RankingUserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    public ResponseEntity<RankingListResponse> getRanking() {
        RankingUserInfoResponse response = new RankingUserInfoResponse(null, null, null, null, null);
        List<RankingUserInfoResponse> result = List.of(response);
        return ResponseEntity.ok(new RankingListResponse(result));
    }
}
