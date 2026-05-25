package jensen.johan.cloudstoreuserservice.model.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank
    String name,
    @Email
    @NotBlank
    String email,

    @Size(min = 6)
    String password
) {

}

