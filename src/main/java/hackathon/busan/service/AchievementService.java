package hackathon.busan.service;

import hackathon.busan.dto.request.ScrapAchievementRequest;
import hackathon.busan.entity.Account;
import hackathon.busan.entity.Achievement;
import hackathon.busan.entity.AchievementScrap;
import hackathon.busan.entity.Mission;
import hackathon.busan.repository.AccountRepository;
import hackathon.busan.repository.AchievementRepository;
import hackathon.busan.repository.AchievementScrapRepository;
import hackathon.busan.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AchievementService {
    private final AccountRepository accountRepository;
    private final MissionRepository missionRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementScrapRepository achievementScrapRepository;

    public void likeAchievement(final ScrapAchievementRequest request){
        Account user = accountRepository.findById(request.userId()).orElseThrow();
        Achievement achievement = achievementRepository.findById(request.achievementId()).orElseThrow();
        achievementScrapRepository.save(new AchievementScrap(user, achievement));
    }

    public void cancelLikeAchievement(final ScrapAchievementRequest request){
        achievementScrapRepository.findByAccountIdAndAchivementId();
        achievementScrapRepository.save(new AchievementScrap(user, achievement));
    }
}
