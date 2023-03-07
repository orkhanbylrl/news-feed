package app.dto;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class UserResPas {
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
