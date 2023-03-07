package app.controller;

import app.parser.data.TechCrunchParser;
import app.parser.data.DroidLifeParser;
import app.parser.data.TechStartupsParser;
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
    private final DroidLifeParser droidLifeParser;
    private final TechStartupsParser techStartupsParser;

    @GetMapping("/news_feed")
    public String showDesignForm(Model model) {
        var a = techCrunchParser.getArticles();
        var b = droidLifeParser.getArticles();
        var c = techStartupsParser.getArticles();
        model.addAttribute("articleList", a);
        model.addAttribute("articleList", b);
        model.addAttribute("articleList", c);
        return "main-page";
    }



}