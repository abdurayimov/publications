package name.lenmar.publications.controller;

import name.lenmar.publications.model.Article;
import name.lenmar.publications.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAll();
    }

    @GetMapping("{id}")
    public Article getArticleById(@PathVariable Long id) {
        return articleService.get(id);
    }

    @PostMapping
    public Article createNewArticle(@RequestBody Article article) {
        return articleService.create(article);
    }

    @PutMapping("{id}")
    public Article editArticleById(@PathVariable Long id, @RequestBody Article article) {
        return articleService.update(id, article);
    }

    @DeleteMapping("{id}")
    public void deleteArticleById(@PathVariable Long id) {
        articleService.delete(id);
    }
}
