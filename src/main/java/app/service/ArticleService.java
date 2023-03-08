package app.service;

import app.entity.Article;
import app.parser.Website;

import java.util.List;

public interface ArticleService {

    void save(Article article);

    void saveAll(List<Article> articleList);

    void mergeAllArticles(List<Article> articles);

    void updateArticles();

    List<Article> getAll();

    List<Article> getWithout(List<Website> websites);

}
