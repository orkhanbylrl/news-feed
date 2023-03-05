package app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


// Launcher
@SpringBootApplication
@EnableScheduling
public class Launcher {
    public static void main(String[] args) {
        // aaa
        SpringApplication.run(Launcher.class, args);
    }
}
