package hackathon.busan.service;

import hackathon.busan.dto.request.AchievementDetailRequest;
import hackathon.busan.dto.request.ScrapAchievementRequest;
import hackathon.busan.dto.response.AchievementDetailResponse;
import hackathon.busan.dto.response.AchievementListResponse;
import hackathon.busan.entity.*;
import hackathon.busan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
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
                formatDate(savedAchievement.getCreatedDate())
        );
    }

    // 미션 인증글 리스트 조회
    public AchievementListResponse getAchievementList(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow();

        List<Achievement> achievements = achievementRepository.findByMission(mission);

        List<AchievementDetailResponse> responses = achievements.stream()
                .map(a -> new AchievementDetailResponse(a.getId(), a.getAccount().getId(), a.getMission().getId(),
                        a.getContent(), null, achievementScrapRepository.countById(a.getId()), formatDate(a.getCreatedDate())))
                .collect(Collectors.toList());
        return new AchievementListResponse(responses);
    }

    private String formatDate(LocalDateTime time) {
        return String.format("%d.%02d.%02d",
                time.getYear(), time.getMonthValue(), time.getDayOfMonth());

    }
}
