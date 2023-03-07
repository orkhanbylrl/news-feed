package app.controller;

import app.entity.Article;
import app.parser.data.TechCrunchParser;
import app.parser.data.DroidLifeParser;
import app.parser.data.TechStartupsParser;
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


    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @GetMapping("/news_feed")
    public String showDesignForm(Model model, @RequestParam(required = false)String news_start
            , @RequestParam(required = false)String news_finish) {
        List<Article> techCrunchParserArticles = techCrunchParser.getArticles();
        articleService.mergeAllArticles(techCrunchParserArticles);
//        var droidLifeParserArticles = droidLifeParser.getArticles();
//        var techStartupsParserArticles = techStartupsParser.getArticles();
        model.addAttribute("articleList", techCrunchParserArticles);
//        model.addAttribute("articleList", droidLifeParserArticles);
//        model.addAttribute("articleList", techStartupsParserArticles);






        log.info(fmt("Start date for search ->  %s",news_start));
        model.addAttribute("news_start",news_start);

        log.info(fmt("Finish date for search ->  %s",news_finish));
        model.addAttribute("news_finish",news_finish);
        return "main-page";
    }



    @GetMapping("")
    public String showFullArticle(Model model){
        return "open-tab";
    }

}