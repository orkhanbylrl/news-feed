package app.controller;

import app.entity.Article;
import app.parser.data.*;
import app.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/")
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticleService articleService;
    private final TechCrunchParser techCrunchParser;
    private final DroidLifeParser droidLifeParser;
    private final TechStartupsParser techStartupsParser;
    private final APNewsParser apNewsParser;
    private final ABCNewsParser abcNewsParser;


    @GetMapping("/news_feed")
    public String showDesignForm(Model model) {
//        List<Article> techCrunchParserArticles = techCrunchParser.getArticles();
//        articleService.mergeAllArticles(techCrunchParserArticles);
        var droidLifeParserArticles = droidLifeParser.getArticles();
        var techStartupsParserArticles = techStartupsParser.getArticles();
//        model.addAttribute("articleList", techCrunchParserArticles);
//        model.addAttribute("articleList", droidLifeParserArticles);
//        model.addAttribute("articleList", techStartupsParserArticles);

        var abcNewsParserArticles = abcNewsParser.getArticles();
        model.addAttribute("articleList", abcNewsParserArticles);
//        var apNewsParserArticles = apNewsParser.getArticles();
//        model.addAttribute("articleList", apNewsParserArticles);



        return "main-page";
    }



    @GetMapping("/news_feed/full_article/{id}")
    public String showFullArticle(Model model){
        List<Article> techCrunchParserArticles = techCrunchParser.getArticles();

        articleService.mergeAllArticles(techCrunchParserArticles);

        model.addAttribute("articleList", techCrunchParserArticles);
        return "open-tab";
    }

}