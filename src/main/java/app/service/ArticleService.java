package app.service;

import app.entity.Article;
import app.parser.Website;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<Article> search(String keyword);
    void save(Article article);

    void saveAll(List<Article> articleList);

    void mergeAllArticles(List<Article> articles);

    void updateArticles();

    List<Article> getAll();

    List<Article> getWithout(List<Website> websites);
    Page<Article> getAll0();

    Optional<Article> getArticle(int id);

    String getFullContent(Article article);



}
