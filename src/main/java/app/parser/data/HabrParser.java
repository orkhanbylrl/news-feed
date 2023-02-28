package app.parser.data;

import app.parser.JsoupParser;
import app.parser.Website;
import app.entity.Article;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabrParser implements JsoupParser {

    List<Article> articles = new ArrayList<>();


    @Override
    public List<Article> getArticles() {
        Document doc = connection("https://habr.com/en/flows/develop/",this.getClass().getName());
        try{
            Elements elements = doc.getElementsByTag("article");
            elements.stream().parallel().forEach(element-> {
                String header = element.select(".post__title_link").text();
                String content = header;
                String link = element.select(".post__title_link").attr("href");
                String image = element.getElementsByTag("img").attr("src");
                LocalDate date = convertStringToDate(element.select(".post__time").text(), dateTimeForm.HABR_FORMAT);

                articles.add(new Article(header, content, link, image, date, Website.Habr));

            });
        } catch (NullPointerException e) {

        }

        return articles;
    }

}
