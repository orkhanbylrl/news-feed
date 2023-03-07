package app.service.impl;

import app.entity.Article;
import app.parser.JsoupParser;
import app.parser.Website;
import app.repository.ArticleRepository;
import app.service.ArticleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repo;


    private final EntityManager entityManager;



    public void saveAll(List<Article> articles) {
         repo.saveAll(articles);
    }

    @Transactional
    public void mergeAllArticles(List<Article> articles) {
        for (Article article : articles) {

            if (!repo.existsByArticleLink(article.getArticleLink())) {
                entityManager.merge(article);
            }
        }
    }

//    public Page<Article> findArticleByDate(String start_date, String finish_date) {
//        LocalDate date1 = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        LocalDate date2 = LocalDate.parse(finish_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        return articleRepository.findBySiteInAndDateBetweenOrderByDateDesc(date1, date2);
//    }



    public Stream<JsoupParser> getNewsParsers() {

        return EnumSet.allOf(Website.class).stream().map(site -> site.getParser());

    }

    @Override
    public void save(Article article) {
        repo.save(article);
    }
}
