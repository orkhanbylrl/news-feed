package app.service;

import app.entity.Article;
import app.parser.JsoupParser;
import app.parser.Website;
import app.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;


    public List<Article> saveAll(List<Article> articles) {
        return articleRepository.saveAll(articles);
    }



    public Stream<JsoupParser> getNewsParsers() {

        return EnumSet.allOf(Website.class).stream().map(site -> site.getParser());

    }
}
