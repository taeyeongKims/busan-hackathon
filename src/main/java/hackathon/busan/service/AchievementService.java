package hackathon.busan.service;

import hackathon.busan.dto.request.AchievementDetailRequest;
import hackathon.busan.dto.request.ScrapAchievementRequest;
import hackathon.busan.dto.response.AchievementDetailResponse;
import hackathon.busan.dto.response.AchievementListResponse;
import hackathon.busan.dto.response.ScrapAchievementListResponse;
import hackathon.busan.dto.s3Dto.UploadAchievementRequest;
import hackathon.busan.entity.*;
import hackathon.busan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final S3Service s3Service;
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
        Account user = accountRepository.findById(request.getUserId()).orElseThrow();
        Mission mission = missionRepository.findById(request.getMissionId()).orElseThrow();
        Achievement newAchievement = new Achievement(user, mission, request.getContent());
        // 미션 인증 저장
        Achievement savedAchievement = achievementRepository.save(newAchievement);
        // 이미지 저장 및 url 획득
        List<String> urls = s3Service.uploadAchievement(new UploadAchievementRequest(request.getImages(), request.getUserId(), request.getMissionId()));
        // 미션 진행 사항 완료로 변경 및 저장
        MissionProgress missionProgress = missionProgressRepository.findByAccountIdAndMissionId(request.getUserId(), request.getMissionId()).orElseThrow();
        missionProgress.setStatusCompleted();
        missionProgressRepository.save(missionProgress);

        return new AchievementDetailResponse(
                savedAchievement.getId(),
                user.getId(),
                mission.getId(),
                savedAchievement.getContent(),
                urls,
                savedAchievement.getLikeCount(),
                formatDate(savedAchievement.getCreatedDate())
        );
    }

    public ScrapAchievementListResponse getLikeAchievement(final Long userId) {
        List<Long> achievementIds = achievementScrapRepository.findAchievementIdsByAccountId(userId);
        List<Achievement> likeAchievement = achievementRepository.findAllById(achievementIds);
        List<AchievementDetailResponse> achievementDetailResponses = likeAchievement.stream().map(achievement ->
                {
                    List<String> urls = imageRepository.findUrlsByAccountIdAndMissionId(achievement.getAccount().getId(), achievement.getMission().getId());
                    return new AchievementDetailResponse(
                            achievement.getId(),
                            achievement.getAccount().getId(),
                            achievement.getMission().getId(),
                            achievement.getContent(),
                            urls,
                            achievement.getLikeCount(),
                            formatDate(achievement.getCreatedDate())
                    );
                }
        ).collect(Collectors.toList());
        return new ScrapAchievementListResponse(achievementDetailResponses);
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
