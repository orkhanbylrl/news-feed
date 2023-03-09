package app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPassForm {
    @NotBlank
    String email;
}
