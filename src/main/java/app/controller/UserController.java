package app.controller;

import app.dto.UserRq;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("login")
    public ModelAndView login_page(){

        return new ModelAndView("index");
    }

    @PostMapping("login/handle")
    public void handle_login(){

    }

    @GetMapping("reg")
    public ModelAndView register_page(){

        return new ModelAndView("registration");
    }

    @PostMapping("reg/handle")
    public void handle_register(@RequestBody UserRq userRq){

    }


}
