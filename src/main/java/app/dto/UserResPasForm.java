package app.dto;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserResPasForm {
    @NotNull
    @Size(min = 3, max = 10)
    private String password;
    @NotNull
    @Size(min = 3, max = 10)
    private String repPassword;

    private String token;

    @AssertTrue(message = "Passwords should match")
    public boolean isPasswordsEqual() {
        return password.equals(repPassword);
    }

}
