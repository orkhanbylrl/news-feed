package app.service;

import app.entity.Article;
import app.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository repository;
    @Override
    public void save(Article article) {
        repository.save(article);
    }

    @Override
    public boolean isExist(String newsTitle) {
        List<Article> allArticles = repository.findAll();
        for (Article n: allArticles) {
            if (n.getHeader().equals(newsTitle)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Article> getArticles() {
        return repository.findAll();
    }
}
