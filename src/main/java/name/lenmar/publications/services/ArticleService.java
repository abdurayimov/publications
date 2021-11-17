package name.lenmar.publications.services;

import name.lenmar.publications.model.Article;
import name.lenmar.publications.model.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article get(Long id) {
        Optional<Article> optional = articleRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("Book not found");
    }

    public List<Article> getAll() {
        Iterator<Article> iterator = articleRepository.findAll().iterator();

        List<Article> articles = new ArrayList<>();
        while (iterator.hasNext()) {
            articles.add(iterator.next());
        }
        return articles;
    }

    public Article create(Article article) {
        Article saved = articleRepository.save(article);
        return saved;
    }

    public Article update(Long id, Article article) {
        Article original = get(id);
        original.setTitle(article.getTitle());
        original.setAuthor(article.getAuthor());
        original.setContent(article.getContent());
        original.setDateOfPublishing(article.getDateOfPublishing());

        Article updated = articleRepository.save(original);
        return updated;
    }

    public void delete(Long id) {
        Article article = get(id);
        articleRepository.delete(article);
    }
}
