package app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;


@Data
@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private ArrayList<String> roles = new ArrayList<>();
}
