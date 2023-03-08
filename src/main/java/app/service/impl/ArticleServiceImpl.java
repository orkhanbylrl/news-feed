package app.service.impl;

import app.entity.Article;
import app.parser.JsoupParser;
import app.parser.Website;
import app.parser.data.*;
import app.repository.ArticleRepository;
import app.service.ArticleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repo;
    private final TechCrunchParser techCrunchParser;
    private final DroidLifeParser droidLifeParser;
    private final TechStartupsParser techStartupsParser;
    private final ABCNewsParser abcNewsParser;
    private final APNewsParser apNewsParser;





    public void saveAll(List<Article> articles) {
         repo.saveAll(articles);
    }

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
    public Page<Article> getAll0() {

        return repo.findAll(PageRequest.of(10, 20));
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


    public Stream<JsoupParser> getNewsParsers() {

        return EnumSet.allOf(Website.class).stream().map(site -> site.getParser());

    }

    @Override
    public List<Article> search(String keyword) {

        if(keyword != null)
            return repo.findAllByHeaderContainingIgnoreCase(keyword);
        return getAll();
    }

    @Override
    public void save(Article article) {
        repo.save(article);
    }
}
