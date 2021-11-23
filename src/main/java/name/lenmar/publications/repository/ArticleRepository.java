package name.lenmar.publications.repository;

import name.lenmar.publications.entity.Article;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
}
