package name.lenmar.publications.service;

import name.lenmar.publications.exception.ArticleNotFoundException;
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
        /*Optional<Article> optional = articleRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("Article not found");*/
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
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
        return articleRepository.save(article);
    }

    public Article update(Long id, Article newArticle) {
        /*Article original = get(id);
        original.setTitle(article.getTitle());
        original.setAuthor(article.getAuthor());
        original.setContent(article.getContent());
        original.setDateOfPublishing(article.getDateOfPublishing());

        return articleRepository.save(original);*/
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(newArticle.getTitle());
                    article.setAuthor(newArticle.getAuthor());
                    article.setContent(newArticle.getContent());
                    article.setDateOfPublishing(newArticle.getDateOfPublishing());
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return articleRepository.save(newArticle);
                });
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
