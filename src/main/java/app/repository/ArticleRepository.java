package app.repository;

import app.entity.Article;
import app.parser.Website;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Boolean existsByArticleLink(String link);

    List<Article> findAllByOrderByDateAscHeaderAsc();

    List<Article> findBySiteNotInOrderByDateAscHeaderAsc(List<Website> websites);

    List<Article> findAllByHeaderContainingIgnoreCase(String keyword);

}