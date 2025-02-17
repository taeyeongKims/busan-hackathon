package hackathon.busan.service;

import hackathon.busan.dto.request.AchievementDetailRequest;
import hackathon.busan.dto.request.ScrapAchievementRequest;
import hackathon.busan.dto.response.AchievementDetailResponse;
import hackathon.busan.dto.s3Dto.UploadAchievementRequest;
import hackathon.busan.entity.*;
import hackathon.busan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                savedAchievement.getCreatedDate(),
                savedAchievement.getUpdatedDate()
        );
    }
}
