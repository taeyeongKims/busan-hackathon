package hackathon.busan.service;

import hackathon.busan.dto.request.AccountLoginRequest;
import hackathon.busan.dto.request.AccountRegisterRequest;
import hackathon.busan.dto.response.AccountUserInfoResponse;
import hackathon.busan.entity.Account;
import hackathon.busan.entity.Location;
import hackathon.busan.repository.AccountRepository;
import hackathon.busan.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;

    public AccountUserInfoResponse loginAccount(AccountLoginRequest request) {
        Account user = accountRepository.findByLoginIdAndPassword(request.loginId(), request.password());
        Account account = accountRepository.save(user);

        return new AccountUserInfoResponse(account.getId(), account.getNickname(),
                account.getLocation());
        /*.getZipcode(), account.getLocation().getAddress(), account.getLocation().getDetailAddress(),
                account.getLocation().getSido(), account.getLocation().getSigugun(), account.getLocation().getDong());*/
    }

    public void registerAccount(AccountRegisterRequest request) {
        Location location;
        if(locationRepository.existsByZipcode(request.location().getZipcode())) {
            location = locationRepository.findByZipcode(request.location().getZipcode());
        } else {
            location = new Location(request.location().getZipcode(), request.location().getAddress(), request.location().getDetailAddress(),
                    request.location().getSido(), request.location().getSigugun(), request.location().getDong());

            locationRepository.save(location); // 위치 저장
        }

        // 비밀번호 암호화 추가

        Account account = new Account(location, request.loginId(), request.password(), request.nickname());

        accountRepository.save(account);

    }
}
