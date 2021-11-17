package name.lenmar.publications.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article")
public class ArticleController {

    @GetMapping
    public String getAllArticles() {
        return "All articles";
    }

    @GetMapping("{id}")
    public String getArticleById(@PathVariable Long id) {
        return "Article by id" + id;
    }

    @PostMapping
    public String createNewArticle() {
        return "New article is created";
    }

    @PutMapping("{id}")
    public String editArticleById(@PathVariable Long id) {
        return "Article by id is updated";
    }

    @DeleteMapping("{id}")
    public String deleteArticleById(@PathVariable Long id) {
        return "Article by id is deleted";
    }
}
