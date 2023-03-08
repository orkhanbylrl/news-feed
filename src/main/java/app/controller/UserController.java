package app.controller;

import app.config.jwt.JwtService;
import app.dto.UserLoginForm;
import app.dto.UserRegForm;

import app.dto.UserResPasForm;
import app.entity.PassResetToken;
import app.entity.User;
import app.exception.MessageFailedException;
import app.exception.UserNotFoundException;
import app.service.ArticleService;
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

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.*;

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
        String token;

        if(result.hasErrors()){
            model.addAttribute("userLog", new UserLoginForm());
            return "login";
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userLog.getEmail(),
                userLog.getPassword()
        );

        Authentication authenticate = authManager.authenticate(authToken);

        if(authenticate.isAuthenticated()){
            try{
                token = jwtService.generateToken(userService.loadUserByUsername(userLog.getEmail()));
            } catch (Exception e) {
                throw new UserNotFoundException(e);
            }
            session.setAttribute("Authorization", "Bearer " + token);
            session.setAttribute("auth", authenticate);
            userService.forToken.put("Authorization", "Bearer " + token);
            userService.forToken.put("auth", authenticate);
        }

        return "redirect:/article/news_feed";
    }

    @GetMapping("/reg")
    public String register_page(Model model){
        model.addAttribute("userReg", new UserRegForm());
        return "registration";
    }

    @PostMapping("/handle_reg")
    public String handle_register(@ModelAttribute @Valid UserRegForm userReg, BindingResult result, Model model){

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
    public void forgot_handler(HttpServletRequest rq)  {

        String email = rq.getParameter("email");
        if(userService.isUserExist(email)){
            System.out.println("user exists");
            User user = userService.getUser(email).get();
            String token = UUID.randomUUID().toString();

            tokenService.createToken(user, token);
            String link = Utility.getSiteURL(rq) + "/user/reset_password?token=" + token;

            String subject = "Email for reset password";
            String body = Utility.setBody(link);

            try{
                emailClientService.sendEmailWithAttachment(email, subject, body);
            } catch (MessagingException e) {
                throw new MessageFailedException(e + "error while sending message");
            }
        }
    }

    @GetMapping("/reset_password")
    public String reset_pass(@RequestParam(value = "token") String token, Model model){
        Optional<PassResetToken> resetToken = tokenService.getToken(token);

        if(resetToken.isPresent() && tokenService.isValid(resetToken.get())){
            UserResPasForm userResPas = new UserResPasForm();
            userResPas.setToken(token);
            model.addAttribute("userResPas", userResPas);
            return "reset-password";
        }
        else {
            return "noToken";
        }

    }

    @PostMapping("/handle_reset")
    public String handle_reset(@ModelAttribute @Valid UserResPasForm userResPas, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("userResPas", new UserResPasForm());
            return "reset-password";
        }

        User user = tokenService.getUser(userResPas.getToken());
        user.setPassword(encoder.encode(userResPas.getPassword()));
        userService.saveUser(user);
        tokenService.deleteToken(user);

        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String  logout(){
        return "redirect:/user/login";
    }

}
