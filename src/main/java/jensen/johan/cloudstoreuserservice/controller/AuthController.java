package jensen.johan.cloudstoreuserservice.controller;

import jakarta.validation.Valid;
import jensen.johan.cloudstoreuserservice.model.user.dto.AuthResponse;
import jensen.johan.cloudstoreuserservice.model.user.dto.LoginRequest;
import jensen.johan.cloudstoreuserservice.model.user.dto.RegisterRequest;
import jensen.johan.cloudstoreuserservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public AuthResponse getMe(Authentication authentication) {
        return authService.me(authentication);
    }
}
