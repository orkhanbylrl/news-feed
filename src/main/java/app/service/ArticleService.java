package app.service;

import app.entity.Article;
import app.parser.JsoupParser;
import app.parser.Website;
import app.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

//    public Page<Article> findArticleByDate(String start_date, String finish_date) {
//        LocalDate date1 = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        LocalDate date2 = LocalDate.parse(finish_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        return articleRepository.findBySiteInAndDateBetweenOrderByDateDesc(date1, date2);
//    }



    public Stream<JsoupParser> getNewsParsers() {

        return EnumSet.allOf(Website.class).stream().map(site -> site.getParser());

    }
}
