package app.dto;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRq {
    private final String fullName;
    private final String email;
    private final String password;
}
