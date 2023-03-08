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
public class ABCNewsParser implements JsoupParser {
    List<Article> articles = new ArrayList<>();

    @Override
    public List<Article> getArticles() {
        try {
            Document doc = Jsoup.connect("https://abcnews.go.com/Business")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0)")
                    .timeout(300000)
                    .referrer("http://www.google.com")
                    .get();
            Elements elements = doc.getElementsByClass("ContentRoll");
            for (Element element : elements) {
                String header = element.select(".AnchorLink").text().substring(0, 15);
                String content = element.select(".ContentRoll__Desc").text();
                String link = Objects.requireNonNull(Objects.requireNonNull(element.select(".AnchorLink").first()).select("a").first()).attr("href");
                String image = Objects.requireNonNull(Objects.requireNonNull(element.select(".ContentRoll__Image").first()).select("img").first()).attr("src");
                LocalDate date = convertStringToDate(element.select("[datetime]").text(), DateTimeFormatter.ofPattern("MMM dd, uuuu"));
                Document doc1 = Jsoup.connect(link)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0)")
                        .timeout(300000)
                        .referrer("https://www.google.com")
                        .get();
                String fullContent = doc1.getElementsByClass("xvlf ZRif TKoO eaKK").text();

                Article article = new Article(header, content, link, image, date, Website.ABCNews);
                articles.add(article);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articles;
    }
}
