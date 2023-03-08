package app.service;

import app.entity.Article;

import java.util.List;

public interface ArticleService {

    void save(Article article);

    void saveAll(List<Article> articleList);

    void mergeAllArticles(List<Article> articles);

    void updateArticles();

    List<Article> getAll();

}
