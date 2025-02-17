package hackathon.busan.service;

import hackathon.busan.dto.request.AchievementDetailRequest;
import hackathon.busan.dto.request.ScrapAchievementRequest;
import hackathon.busan.dto.response.AchievementDetailResponse;
import hackathon.busan.entity.*;
import hackathon.busan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AchievementService {
    private final AccountRepository accountRepository;
    private final MissionRepository missionRepository;
    private final AchievementRepository achievementRepository;
    private final MissionProgressRepository missionProgressRepository;
    private final AchievementScrapRepository achievementScrapRepository;
    private final ImageRepository imageRepository;

    public void likeAchievement(final ScrapAchievementRequest request){
        Account user = accountRepository.findById(request.userId()).orElseThrow();
        Achievement achievement = achievementRepository.findById(request.achievementId()).orElseThrow();
        achievementScrapRepository.save(new AchievementScrap(user, achievement));
    }

    public void cancelLikeAchievement(final ScrapAchievementRequest request){
        AchievementScrap deletedScrap = achievementScrapRepository.findByAccountIdAndAchievementId(request.userId(), request.achievementId()).orElseThrow();
        achievementScrapRepository.delete(deletedScrap);
    }

    public AchievementDetailResponse postAchievement(final AchievementDetailRequest request) {
        Account user = accountRepository.findById(request.userId()).orElseThrow();
        Mission mission = missionRepository.findById(request.missionId()).orElseThrow();
        Achievement newAchievement = new Achievement(user, mission, request.content());
        Achievement savedAchievement = achievementRepository.save(newAchievement);

        List<Image> imageList = request.images().stream().map(image -> new Image(user, mission, null))
                .collect(Collectors.toList());
        List<String> images = imageRepository.saveAll(imageList).stream().map(Image::getUrl).collect(Collectors.toList());
        MissionProgress missionProgress = missionProgressRepository.findByAccountIdAndMissionId(request.userId(), request.missionId()).orElseThrow();

        missionProgress.setStatusCompleted();
        missionProgressRepository.save(missionProgress);

        return new AchievementDetailResponse(
                savedAchievement.getId(),
                user.getId(),
                mission.getId(),
                savedAchievement.getContent(),
                images,
                savedAchievement.getLikeCount(),
                savedAchievement.getCreatedDate(),
                savedAchievement.getUpdatedDate()
        );
    }
}
