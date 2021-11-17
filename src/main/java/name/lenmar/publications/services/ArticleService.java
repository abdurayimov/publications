package name.lenmar.publications.services;

import name.lenmar.publications.model.Article;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ArticleService {

    public Article get(Long id) {
        // TODO getArticle
        return new Article();
    }

    public List<Article> getAll() {
        // TODO getAllArticles
        return Collections.emptyList();
    }

    public Article create(Article article) {
        // TODO createArticle
        return article;
    }

    public Article update(Long id, Article article) {
        // TODO updateArticle
        return article;
    }

    public void delete(Long id) {
        //TODO deleteArticle
    }
}
