package app.controller;

import app.dto.UserLoginForm;
import app.dto.UserRegForm;
import app.dto.UserRq;
import app.entity.User;
import app.service.UserService;
import app.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("login")
    public String login_page(Model model){
        model.addAttribute("userLog", new UserLoginForm());
        return "index";
    }

    @PostMapping("handle_login")
    public String handle_login(@ModelAttribute @Valid UserLoginForm userLog, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("userLog", new UserLoginForm());
            return "index";
        }

        return "redirect:/url that will be redirected after login";
    }

    @GetMapping("reg")
    public String register_page(Model model){
        model.addAttribute("userReg", new UserRegForm());
        return "registration";
    }

    @PostMapping("handle_reg")
    public String handle_register(@ModelAttribute @Valid UserRegForm userReg, BindingResult result, Model model){
        if(result.hasErrors() || userService.isUserExist(userReg.getEmail())){
            model.addAttribute("userReg", new UserRegForm());
            return "registration";
        }

        User created = new User(userReg.getFullName(), userReg.getEmail(), userReg.getPassword(),
                Arrays.asList("USER"));

        userService.saveUser(created);

        return "redirect:/user/login";
    }


}
