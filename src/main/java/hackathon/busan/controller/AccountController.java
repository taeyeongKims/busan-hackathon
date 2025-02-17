package hackathon.busan.controller;

import hackathon.busan.dto.request.AccountLoginRequest;
import hackathon.busan.dto.request.AccountRegisterRequest;
import hackathon.busan.dto.response.AccountUserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountController {

    @Operation(summary = "로그인")
    @GetMapping("/login")
    public ResponseEntity<AccountUserInfoResponse> loginUser(@RequestBody AccountLoginRequest request) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "회원가입")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AccountRegisterRequest request) {
        return ResponseEntity.ok("회원가입이 성공하였습니다.");
    }
}
