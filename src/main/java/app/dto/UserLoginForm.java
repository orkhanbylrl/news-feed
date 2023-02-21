package app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginForm {

    @NotBlank
    String email;
    @NotBlank
    String password;
}
