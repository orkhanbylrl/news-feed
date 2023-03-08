package app.controller;

import app.entity.Article;
import app.entity.User;
import app.parser.data.TechCrunchParser;
import app.parser.data.DroidLifeParser;
import app.parser.data.TechStartupsParser;
import app.service.ArticleService;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticleService articleService;
    private final UserService userService;



    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @GetMapping("/news_feed")
    public String showDesignForm(Model model, Principal principal) {

        User user = userService.getUser(principal.getName()).get();

        articleService.updateArticles();

        List<Article> all = articleService.getAll();


        model.addAttribute("articleList", all);
        model.addAttribute("user", user);


        return "main-page";
    }



    @GetMapping("/news_feed/full_article/{id}")
    public String showFullArticle(Model model){
//        List<Article> techCrunchParserArticles = techCrunchParser.getArticles();
//
//        articleService.mergeAllArticles(techCrunchParserArticles);
//
//        model.addAttribute("articleList", techCrunchParserArticles);
        return "open-tab";
    }

    @GetMapping("/disable")
    public String disable(Model model){
        return "disable-news";
    }

}