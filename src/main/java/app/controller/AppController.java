package app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("app")
public class AppController {

    @GetMapping
    public RedirectView getting_login(){
        return new RedirectView("user/login");
    }

}
