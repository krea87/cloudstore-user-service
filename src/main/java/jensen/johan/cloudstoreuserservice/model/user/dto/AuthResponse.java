package jensen.johan.cloudstoreuserservice.model.user.dto;

public record AuthResponse(
        String token,
        String email
) {
}
