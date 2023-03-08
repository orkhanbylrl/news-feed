package app.parser.data;

import app.entity.Article;
import app.parser.JsoupParser;
import app.parser.Website;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class APNewsParser implements JsoupParser {
    List<Article> articles = new ArrayList<>();

    @Override
    public List<Article> getArticles() {
        try {
            Document doc = Jsoup.connect("https://apnews.com/hub/technology?utm_source=apnewsnav&utm_medium=navigation")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0)")
                    .timeout(300000)
                    .referrer("http://www.google.com")
                    .get();
            Elements elements = doc.getElementsByClass("hub-0-2-22 hub-0-0-2-46 Hub fluid-wrapper with-image-desktop with-image-mobile with-copy");
            for (Element element : elements) {
                String header = element.select(".CardHeadline").text();
                String content = element.select(".content").text();
                String link = Objects.requireNonNull(Objects.requireNonNull(element.select(".Component-headline-0-2-115").first()).select("a").first()).attr("href");
                String image = Objects.requireNonNull(Objects.requireNonNull(element.select(".image-0-2-172 image-0-0-2-173").first()).select("img").first()).attr("src");
                LocalDate date = convertStringToDate(element.select("[datetime]").text(), DateTimeFormatter.ofPattern("MMM dd, uuuu"));
                Document doc1 = Jsoup.connect(link)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0)")
                        .timeout(300000)
                        .referrer("https://www.google.com")
                        .get();
                String fullContent = doc1.getElementsByClass("Article").text();

                Article article = new Article(header, content, link, image,date, Website.APNews);
                articles.add(article);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articles;
    }
}
