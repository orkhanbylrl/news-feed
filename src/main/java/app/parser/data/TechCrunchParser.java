package app.parser.data;

import app.entity.Article;
import app.parser.JsoupParser;
import app.parser.Website;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TechCrunchParser implements JsoupParser {

    List<Article> articles = new ArrayList<>();



    @Override
    @Scheduled(fixedDelay = 10000)
    public List<Article> getArticles() {
        try{
            Document doc = Jsoup.connect("https://techcrunch.com/category/startups/").get();
            Elements elements = doc.getElementsByClass("post-block");
            for (Element element: elements){
                String header = element.select(".post-block__title__link").text();
                String content = element.select(".post-block__content").text();
                String link = Objects.requireNonNull(Objects.requireNonNull(element.select(".post-block__title").first()).select("a").first()).attr("href");
                String image = Objects.requireNonNull(Objects.requireNonNull(element.select(".post-block__media").first()).select("img").first()).attr("src");
                Article article = new Article(header, content, link, image, Website.TechCrunch);
                articles.add(article);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articles;
    }


}
