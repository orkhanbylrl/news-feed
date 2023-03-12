package app.service.impl;

import app.entity.Article;
import app.parser.Website;
import app.parser.data.APNewsParser;
import app.parser.data.DroidLifeParser;
import app.parser.data.TechCrunchParser;
import app.parser.data.TechStartupsParser;
import app.repository.ArticleRepository;
import app.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repo;
    private final TechCrunchParser techCrunchParser;
    private final DroidLifeParser droidLifeParser;
    private final TechStartupsParser techStartupsParser;
    private final APNewsParser apNewsParser;


    public void mergeAllArticles(List<Article> articles) {
        for (Article article : articles) {

            if (!repo.existsByArticleLink(article.getArticleLink())) {
                repo.save(article);
            }
        }
    }

    @Override
    public void updateArticles() {
        List<Article> allArticles = new ArrayList<>();
        List<Article> techCrunchParserArticles = techCrunchParser.getArticles();
        List<Article> droidLifeParserArticles = droidLifeParser.getArticles();
        List<Article> techStartupsParserArticles = techStartupsParser.getArticles();
        List<Article> apNewsParserArticles = apNewsParser.getArticles();
        allArticles.addAll(techCrunchParserArticles);
        allArticles.addAll(droidLifeParserArticles);
        allArticles.addAll(techStartupsParserArticles);
        allArticles.addAll(apNewsParserArticles);

        this.mergeAllArticles(allArticles);

    }

    public List<Article> getAll() {
        return repo.findAllByOrderByDateAscHeaderAsc();
    }


    @Override
    public Optional<Article> getArticle(int id) {
        return repo.findById(id);
    }

    @Override
    public String getFullContent(Article article) {
        String content = null;
        if(article.getSite() == Website.APNews){
            content = apNewsParser.getFullContentAP(article.getArticleLink());
        } else if (article.getSite() == Website.DroidLife) {
            content = droidLifeParser.getFullContentDroid(article.getArticleLink());
        }
        else if(article.getSite() == Website.TechCrunch){
            content = techCrunchParser.getFullContentTC(article.getArticleLink());
        } else if (article.getSite() == Website.TechStartups) {
            content = techStartupsParser.getFullContentTS(article.getArticleLink());
        }
        return content;
    }

    @Override
    public List<Article> getWithout(List<Website> websites){
        return repo.findBySiteNotInOrderByDateAscHeaderAsc(websites);
    }

    @Override
    public List<Article> search(String keyword) {

        if(keyword != null)
            return repo.findAllByHeaderContainingIgnoreCase(keyword);
        return getAll();
    }

}
