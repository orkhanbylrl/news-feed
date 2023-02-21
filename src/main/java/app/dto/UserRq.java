package app.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
public class UserRq {
    String fullName;
    String email;
    String password;
}
