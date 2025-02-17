package hackathon.busan.service;

import hackathon.busan.dto.request.ChangeNicknameRequest;
import hackathon.busan.dto.response.MyPageInfoResponse;
import hackathon.busan.entity.Account;
import hackathon.busan.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {
    private final AccountRepository accountRepository;

    public MyPageInfoResponse getMyInfo(final Long userId) {
        Account user = accountRepository.findById(userId).orElseThrow();
        return new MyPageInfoResponse(
                user.getProfile(),
                user.getNickname(),
                user.getPoint(),
                user.getMissionCount(),
                user.getAchievementCount()
        );
    }

    public MyPageInfoResponse changeNickname(final ChangeNicknameRequest request) {
        Account user = accountRepository.findById(request.userId()).orElseThrow();
        user.setNickname(request.nickname());
        Account savedUser = accountRepository.save(user);
        return new MyPageInfoResponse(
                savedUser.getProfile(),
                savedUser.getNickname(),
                savedUser.getPoint(),
                savedUser.getMissionCount(),
                savedUser.getAchievementCount()
        );
    }
}
