package name.lenmar.publications.service;

import name.lenmar.publications.exception.ArticleNotFoundException;
import name.lenmar.publications.model.Article;
import name.lenmar.publications.model.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article get(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public Page<Article> getAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
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
