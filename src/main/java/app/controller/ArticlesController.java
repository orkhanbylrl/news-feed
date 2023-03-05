package app.controller;

import app.entity.Article;
import app.service.ArticleService;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import app.entity.Article;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ArticlesController {
    private final ArticleService articleService;
    private final UserService userService;


    @GetMapping("/news_feed")
    public String getArticles(Model model){
        model.addAttribute("articles", new ArrayList<>());
        return "main-page";
    }



}