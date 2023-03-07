package app.parser.data;

import app.entity.Article;
import app.parser.JsoupParser;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import app.parser.Website;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TechStartupsParser implements JsoupParser {
    List<Article> articles = new ArrayList<>();


    @Override
    public List<Article> getArticles() {
        Document doc = connection("https://techstartups.com/",this.getClass().getName());
        try{
            Elements elements = doc.select(".sidebar_content .post");
            elements.stream().parallel().forEach(element->{
                String header = element.select(".post_header_title").first().getElementsByTag("a").first().text();
                String content = element.select(".post_header_title > p").text();
                String link = element.select(".post_header_title").first()
                        .getElementsByTag("a").first().attr("href");
                String imageLink = element.getElementsByTag("img").first().attr("src");

                articles.add(new Article(header, content, link, imageLink, Website.TechStartups));
            });
        } catch (NullPointerException e) {

        }
        return articles;
    }
}
