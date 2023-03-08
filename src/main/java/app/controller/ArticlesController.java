package app.controller;

import app.entity.Article;
import app.entity.User;
import app.parser.Website;
import app.parser.data.TechCrunchParser;
import app.parser.data.DroidLifeParser;
import app.parser.data.TechStartupsParser;
import app.service.ArticleService;
import app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    private static List<Website> disabled = new ArrayList<>();



    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @GetMapping("/news_feed")
    public String showDesignForm(Model model, Principal principal) {
        User user = userService.getUser(principal.getName()).get();
        articleService.updateArticles();
        List<Article> all;

        if(disabled.isEmpty())
            all = articleService.getAll();
        else
            all = articleService.getWithout(disabled);



        model.addAttribute("articleList", all);
        model.addAttribute("user", user);


        return "main-page";
    }



    @GetMapping("/full_article/{id}")
    public String showFullArticle(Model model){

        return "open-tab";
    }

    @GetMapping("/disable")
    public String disable(Model model){
        return "disable-news";
    }

    @PostMapping("/handle_disable")
    public String disable_handle(HttpServletRequest rq){
        disabled.clear();
        if(rq.getParameter("ABCNews") != null){
            disabled.add(Website.ABCNews);
        }
        if(rq.getParameter("APNews") != null){
            disabled.add(Website.APNews);
        }
        if(rq.getParameter("DroidLife") != null){
            disabled.add(Website.DroidLife);
        }
        if(rq.getParameter("TechCrunch") != null){
            disabled.add(Website.TechCrunch);
        }
        if(rq.getParameter("TechStartups") != null){
            disabled.add(Website.TechStartups);
        }

        return "redirect:/article/news_feed";
    }

}