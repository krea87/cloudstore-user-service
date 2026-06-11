package jensen.johan.cloudstoreuserservice.service;

import jensen.johan.cloudstoreuserservice.model.user.AppUser;
import jensen.johan.cloudstoreuserservice.model.user.dto.AuthResponse;
import jensen.johan.cloudstoreuserservice.model.user.dto.LoginRequest;
import jensen.johan.cloudstoreuserservice.model.user.dto.RegisterRequest;
import jensen.johan.cloudstoreuserservice.repository.user.AppUserRepository;
import jensen.johan.cloudstoreuserservice.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {


    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AppUserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already in use"
            );
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        AppUser user = new AppUser();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(hashedPassword);
        user.setRole("USER");

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String token = jwtService.generateToken(request.email());
        return new AuthResponse(token, request.email());
    }

    public AuthResponse me(Authentication authentication) {
        String email = authentication.getName();
        return new AuthResponse(jwtService.generateToken(email), email);
    }

}
