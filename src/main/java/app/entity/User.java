package app.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Data
@Entity
@Table(name = "user_table")
public class User {
    // niuuuuuu
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private ArrayList<String> roles = new ArrayList<>();
}
