package name.lenmar.publications.service;

import name.lenmar.publications.error.ArticleNotFoundException;
import name.lenmar.publications.entity.Article;
import name.lenmar.publications.repository.ArticleRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article saveOrUpdate(Long id, Article newArticle) {
        return articleRepository.findById(id)
                .map(x -> {
                    x.setTitle(newArticle.getTitle());
                    x.setAuthor(newArticle.getAuthor());
                    x.setContent(newArticle.getContent());
                    x.setDateOfPublishing(newArticle.getDateOfPublishing());
                    return articleRepository.save(x);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return articleRepository.save(newArticle);
                });
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    public int getCountOfPublishedArticles() {
        LocalDate current = currentDate();
        List<Article> result = articleRepository.findAllWithPublishingDateBefore(current, previousDate(current));
        return result.size();
    }

    private LocalDate previousDate(LocalDate current) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.sql.Date.valueOf(current));
        calendar.add(Calendar.DAY_OF_YEAR, -7);

        Date previous = calendar.getTime();
        return new java.sql.Date(previous.getTime()).toLocalDate();
    }

    private LocalDate currentDate() {
        Date dateToConvert = Calendar.getInstance().getTime();
        /*return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();*/
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
}
