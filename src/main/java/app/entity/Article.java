package app.entity;

import app.parser.Website;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Column(name="header")
    private String header;

    @Column(name="content",columnDefinition="TEXT")
    private String content;

    @Id
    @Column(name="article_link")
    private String articleLink;

    @Column(name="image_link")
    private String imageLink;


    @Column(name="site")
    @Enumerated(EnumType.STRING)
    private Website site;
}
