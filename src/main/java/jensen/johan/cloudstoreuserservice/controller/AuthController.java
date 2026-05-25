package jensen.johan.cloudstoreuserservice.controller;

import jakarta.validation.Valid;
import jensen.johan.cloudstoreuserservice.model.user.dto.AuthResponse;
import jensen.johan.cloudstoreuserservice.model.user.dto.LoginRequest;
import jensen.johan.cloudstoreuserservice.model.user.dto.RegisterRequest;
import jensen.johan.cloudstoreuserservice.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
