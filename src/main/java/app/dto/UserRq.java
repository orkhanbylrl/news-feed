package app.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
public class UserRq {
//    private final String fullName;
    String email;
    String password;
}
