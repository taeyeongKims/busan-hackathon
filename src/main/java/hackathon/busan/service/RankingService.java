package hackathon.busan.service;

import hackathon.busan.dto.response.RankingListResponse;
import hackathon.busan.dto.response.RankingUserInfoResponse;
import hackathon.busan.entity.Account;
import hackathon.busan.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RankingService {
    private final AccountRepository accountRepository;

    public RankingListResponse getRanking() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Account> ranking = accountRepository.findTop10ByTotalCountDesc(pageable);
        List<RankingUserInfoResponse> list = ranking.stream().map(
                account -> new RankingUserInfoResponse(
                        account.getProfile(),
                        account.getNickname(),
                        account.getMissionCount(),
                        account.getAchievementCount(),
                        account.getMissionCount() + account.getAchievementCount()
                )
        ).toList();
        return new RankingListResponse(list);
    }
}