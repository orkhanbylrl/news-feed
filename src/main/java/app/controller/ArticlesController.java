package app.controller;

import app.parser.data.TechCrunchParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ArticlesController {
    private final TechCrunchParser techCrunchParser;


    @GetMapping("/news_feed")
    public String showDesignForm(Model model) {
        var a = techCrunchParser.getArticles();
        model.addAttribute("articleList", a);
        return "main-page";
    }



}