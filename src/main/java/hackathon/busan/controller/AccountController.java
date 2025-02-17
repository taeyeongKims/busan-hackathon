package hackathon.busan.controller;

import hackathon.busan.dto.request.AccountLoginRequest;
import hackathon.busan.dto.request.AccountRegisterRequest;
import hackathon.busan.dto.response.AccountUserInfoResponse;
import hackathon.busan.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<AccountUserInfoResponse> loginUser(@RequestBody AccountLoginRequest request) {
        return ResponseEntity.ok(accountService.loginAccount(request));
    }

    @Operation(summary = "회원가입")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AccountRegisterRequest request) {
        accountService.registerAccount(request);
        return ResponseEntity.ok("회원가입이 성공하였습니다.");
    }
}
