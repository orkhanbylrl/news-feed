package app.entity;

import app.parser.Website;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String header;

    @Column(name="content",columnDefinition="TEXT")
    private String content;
    @Column(name="article_link", unique = true)
    private String articleLink;

    @Column(name="image_link")
    private String imageLink;

    @Column(name="article_date")
    private LocalDate date;

    @Column(name="site")
    @Enumerated(EnumType.STRING)
    private Website site;


    public Article(String header, String content, String articleLink, String imageLink, LocalDate date, Website site) {
        this.header = header;
        this.content = content;
        this.articleLink = articleLink;
        this.imageLink = imageLink;
        this.date = date;
        this.site = site;
    }
}
