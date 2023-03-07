package app.controller;

import app.config.jwt.JwtService;
import app.dto.UserLoginForm;
import app.dto.UserRegForm;

import app.dto.UserResPas;
import app.entity.User;
import app.repository.TokenRepository;
import app.service.EmailClientService;
import app.service.TokenService;
import app.service.UserService;
import app.util.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final EmailClientService emailClientService;
    private final TokenService tokenService;

    @GetMapping("/login")
    public String login_page(Model model){
        model.addAttribute("userLog", new UserLoginForm());
        return "login";
    }

    @PostMapping("/handle_login")
    public String handle_login(@ModelAttribute @Valid UserLoginForm userLog, BindingResult result, Model model, HttpSession session, HttpServletResponse rs){
        System.out.println(userLog);
        System.out.println("handle_login");
        if(result.hasErrors()){
            model.addAttribute("userLog", new UserLoginForm());
            return "login";
        }

        String token;

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userLog.getEmail(),
                userLog.getPassword()
        );

        Authentication authenticate = authManager.authenticate(authToken);

        if(authenticate.isAuthenticated()){
            token = jwtService.generateToken(userService.loadUserByUsername(userLog.getEmail()));
            session.setAttribute("Authorization", "Bearer " + token);
            session.setAttribute("auth", authenticate);
        }

        return "redirect:/news_feed";
    }

    @GetMapping("/reg")
    public String register_page(Model model){
        model.addAttribute("userReg", new UserRegForm());
        return "registration";
    }

    @PostMapping("/handle_reg")
    public String handle_register(@ModelAttribute @Valid UserRegForm userReg, BindingResult result, Model model){
        System.out.println(userReg);
        System.out.println("handle_register");
        if(result.hasErrors() || userService.isUserExist(userReg.getEmail())) {
            model.addAttribute("userReg", new UserRegForm());
            return "registration";
        }

        User created = new User(userReg.getFullName(), userReg.getEmail(), encoder.encode(userReg.getPassword()),
                Arrays.asList("USER"));
        userService.saveUser(created);

        return "redirect:/user/login";
    }


    @GetMapping("/forgot_pass")
    public String forgot_pass(){
        return "forgot-password";
    }

    @PostMapping("/forgot_handler")
    public void forgot_handler(HttpServletRequest rq) throws MessagingException {

        String email = rq.getParameter("email");
        if(userService.isUserExist(email)){
            System.out.println("user exists");
            User user = userService.getUser(email).get();
            String token = UUID.randomUUID().toString();

            tokenService.createToken(user, token);
            String link = Utility.getSiteURL(rq) + "/user/reset_password?token=" + token;

            String subject = "Email for reset password";
            String body = Utility.setBody(link);

           emailClientService.sendEmailWithAttachment(email, subject, body);
        }
    }

    @GetMapping("/reset_password")
    public String reset_pass(Model model){
        model.addAttribute("userResPas", new UserResPas());
        return "reset-password";
    }

    @PostMapping("/handle_reset")
    public String handle_reset(@ModelAttribute @Valid UserResPas userResPas, BindingResult result, Model model, HttpServletRequest rq){
        if(result.hasErrors()){
            model.addAttribute("userResPas", new UserResPas());
            return "reset-password";
        }

        String token = rq.getParameter("token");


        System.out.println(token);

        User u = tokenService.getUser(token);

        System.out.println(u);

        return "redirect:/user/login";
    }






}
