package name.lenmar.publications.repository;

import name.lenmar.publications.entity.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    @Query("select a from Article a where a.dateOfPublishing <= :currentDateTime and a.dateOfPublishing > :previousDateTime")
    List<Article> findAllWithPublishingDateBefore(
            @Param("currentDateTime") LocalDate currentDateTime,
            @Param("previousDateTime") LocalDate previousDateTime);
}
