package hackathon.busan.service;

import hackathon.busan.dto.request.MissionInfoRequest;
import hackathon.busan.dto.request.MissionUserInfoRequest;
import hackathon.busan.dto.response.MissionDetailResponse;
import hackathon.busan.dto.response.MissionInfoResponse;
import hackathon.busan.dto.response.MissionListResponse;
import hackathon.busan.dto.response.ScrapMissionListResponse;
import hackathon.busan.entity.*;
import hackathon.busan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final MissionScrapRepository missionScrapRepository;
    private final AchievementRepository achievementRepository;
    private final MissionProgressRepository progressRepository;

    // 불평등 동네
    private Set<String> priSet = new HashSet<>(Arrays.asList("북구", "사상구", "서구", "동구", "중구", "영도구", "사하구", "강서구"));

    // 모든 미션 조회
    public MissionListResponse getMissionList(String filter, List<String> criteria) {
        List<Mission> allMissionList = missionRepository.findAll();
        List<Mission> missionList = new ArrayList<>(allMissionList);

        if(criteria != null) { // 카테고리 게시글만 필터링
            missionList = allMissionList.stream()
                            .filter(m -> m.getCategories() != null &&
                                    m.getCategories().stream().anyMatch(cate -> criteria.contains(cate.getName())))
                    .collect(Collectors.toList());
        }

        if(filter == null) { // 필터 적용 X, 불평등 동네 우선
            missionList = missionList.stream()
                    .sorted((m1, m2) -> {
                        boolean isM1InPriSet = priSet.contains(m1.getLocation().getSigugun());
                        boolean isM2InPriSet = priSet.contains(m2.getLocation().getSigugun());

                        // priSet에 포함된 Mission을 우선순위로 정렬
                        return Boolean.compare(isM2InPriSet, isM1InPriSet); // isM2InPriSet이 true일 경우 우선순위가 높음
                    })
                    .collect(Collectors.toList());
        } else {
            switch(filter) {
                case "최신순":
                    missionList.sort(Comparator.comparing(Mission::getCreatedDate).reversed());
                    break;
                case "참여자순":
                    missionList.sort((o1, o2) -> (int) (progressRepository.countByMission(o2) - progressRepository.countByMission(o1)));
                    break;
                case "좋아요순":
                    missionList.sort((o1, o2) -> (int) (missionScrapRepository.countByMission(o2) - missionScrapRepository.countByMission(o1)));
                    break;
                default:
                    break;
            }
        }

        List<MissionInfoResponse> responses = missionList.stream()
                .map(m -> new MissionInfoResponse(m.getId(), m.getAccount().getId(), m.getAccount().getNickname(),
                        m.getTitle(), null, missionScrapRepository.countByMission(m), achievementRepository.countByMission(m),
                        formatDate(m.getCreatedDate())))
                .collect(Collectors.toList());

        return new MissionListResponse(responses);
    }

    // 미션 생성
    public void createMission(MissionInfoRequest request) {
        Location location = new Location(request.location().getZipcode(), request.location().getAddress(), request.location().getDetailAddress(),
                request.location().getSido(), request.location().getSigugun(), request.location().getDong());

        locationRepository.save(location); // 위치 저장

        Account account = accountRepository.findById(request.userId())
                .orElseThrow();

        account.setMissionCount(account.getMissionCount() + 1); // 미션 개수 + 1
        account.setPoint(account.getPoint() + 10);
        accountRepository.save(account);

        List<Category> categories = request.category().stream()
                .map(c -> categoryRepository.findByName(c))
                .collect(Collectors.toList());
        Mission mission = new Mission(account, categories, location, request.title(), request.content());

        missionRepository.save(mission); // 미션 저장
    }

    // 미션 상세 조회
    public MissionDetailResponse detailMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow();

        List<String> categories = mission.getCategories().stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());

        return new MissionDetailResponse(mission.getId(), mission.getAccount().getId(), mission.getAccount().getNickname(),
                mission.getTitle(), mission.getContent(), progressRepository.countByMission(mission),
                formatDate(mission.getCreatedDate()), achievementRepository.countByMission(mission),
                categories, null);
    }

    // 미션 찜하기
    public void scrapMission(MissionUserInfoRequest request) {
        Account account = accountRepository.findById(request.userId())
                .orElseThrow();

        Mission mission = missionRepository.findById(request.missionId())
                .orElseThrow();

        MissionScrap scrap = new MissionScrap(account, mission);
        missionScrapRepository.save(scrap);


    }

    // 미션 찜 취소하기
    public void cancelScrapMission(MissionUserInfoRequest request) {
        Account account = accountRepository.findById(request.userId())
                .orElseThrow();

        Mission mission = missionRepository.findById(request.missionId())
                .orElseThrow();

        MissionScrap scrap = missionScrapRepository.findByAccountAndMission(account, mission);
        missionScrapRepository.delete(scrap);
    }

    // 미션 찜 리스트 조회
    public ScrapMissionListResponse getScrapMissionList(Long userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow();

        List<MissionScrap> scrapList = missionScrapRepository.findAllByAccount(account);
        List<MissionInfoResponse> responses = scrapList.stream()
                .map(ms -> new MissionInfoResponse(ms.getMission().getId(), ms.getMission().getAccount().getId(), ms.getMission().getAccount().getNickname(),
                        ms.getMission().getTitle(), null,
                        missionScrapRepository.countByMission(ms.getMission()), achievementRepository.countByMission(ms.getMission()),
                        formatDate(ms.getMission().getCreatedDate())))
                .collect(Collectors.toList());

        return new ScrapMissionListResponse(responses);
    }

    // 미션 참여 신청
    public void applyMission(MissionUserInfoRequest request) {
        Account account = accountRepository.findById(request.userId())
                .orElseThrow();

        Mission mission = missionRepository.findById(request.missionId())
                .orElseThrow();

        MissionProgress progress = new MissionProgress(account, mission);
        progressRepository.save(progress);
    }

    private String formatDate(LocalDateTime time) {
        return String.format("%d.%02d.%02d",
                time.getYear(), time.getMonthValue(), time.getDayOfMonth());

    }
}
