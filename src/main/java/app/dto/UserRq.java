package app.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class UserRq {
    String fullName;
    String email;
    String password;
}
