package app.service.impl;

import app.entity.Article;
import app.parser.JsoupParser;
import app.parser.Website;
import app.parser.data.DroidLifeParser;
import app.parser.data.TechCrunchParser;
import app.parser.data.TechStartupsParser;
import app.repository.ArticleRepository;
import app.service.ArticleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
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
        allArticles.addAll(techCrunchParserArticles);
        allArticles.addAll(droidLifeParserArticles);
        allArticles.addAll(techStartupsParserArticles);

        this.mergeAllArticles(allArticles);

    }

    @Override
    public List<Article> getAll() {

        List<Article> collect = StreamSupport.stream(repo.findAllByOrderByDateAscHeaderAsc().spliterator(), false)
                .collect(Collectors.toList());

        return collect;
    }




    public Stream<JsoupParser> getNewsParsers() {

        return EnumSet.allOf(Website.class).stream().map(site -> site.getParser());

    }

    @Override
    public void save(Article article) {
        repo.save(article);
    }
}
