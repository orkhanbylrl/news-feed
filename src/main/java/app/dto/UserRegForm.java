package app.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegForm {

    @NotBlank
    private String fullName;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Size(min = 3, max = 10)
    private String password;
    @NotNull
    @Size(min = 3, max = 10)
    private String repPassword;


    @AssertTrue(message = "Passwords should match")
    public boolean isPasswordsEqual() {
        return password.equals(repPassword);
    }

}
