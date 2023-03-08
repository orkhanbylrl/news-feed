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
public class DroidLifeParser implements JsoupParser {
    List<Article> articles = new ArrayList<>();

    @Override
    public List<Article> getArticles() {
        try {
            Document doc = Jsoup.connect("https://www.droid-life.com/")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0)")
                    .timeout(300000)
                    .referrer("http://www.google.com")
                    .get();
            Elements elements = doc.getElementsByTag("article");
            elements.stream().parallel().forEach(element->{
                String header = element.select(".preview__title").text();
                String content = element.select(".preview__excerpt").text();
                String link = element.select(".preview__link").first().select("a").first().attr("href");
                String imageLink = "https://www.droid-life.com/"
                        .concat(element.select(".picture").first().select("img").first().attr("src"));
                LocalDate date = convertStringToDate(element.select(".entry-meta__updated").text(), DateTimeFormatter.ofPattern("MMMM d, uuuu"));

                Document doc1;
                try {
                    doc1 = Jsoup.connect(link)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0)")
                            .timeout(300000)
                            .referrer("http://www.google.com")
                            .get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String fullContent = doc1.getElementsByClass("entry-body").text();
                articles.add(new Article(header, content, link, imageLink, fullContent,date, Website.DroidLife));
            });
        } catch (NullPointerException e) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return articles;
    }

}
