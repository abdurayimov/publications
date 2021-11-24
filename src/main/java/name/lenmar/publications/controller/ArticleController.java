package name.lenmar.publications.controller;

import name.lenmar.publications.entity.Article;
import name.lenmar.publications.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // Find all
    @GetMapping("/article")
    public Page<Article> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return articleService.findAll( PageRequest.of(page, size) );
    }

    // Find one
    @GetMapping("/article/{id}")
    public Article getById(@PathVariable @Min(1) Long id) {
        return articleService.findById(id);
    }

    // Save
    @PostMapping("/article")
        @ResponseStatus(code = HttpStatus.CREATED)
        @Transactional
    public Article save(@Valid @RequestBody Article article) {
        return articleService.save(article);
    }

    // Save or update
    @PutMapping("/article/{id}")
        @Transactional
    public Article saveOrUpdate(@Valid @RequestBody Article article, @PathVariable Long id) {
        return articleService.saveOrUpdate(id, article);
    }

    // Delete
    @DeleteMapping("/article/{id}")
    public void deleteOne(@PathVariable Long id) {
        articleService.delete(id);
    }

    // Statistics
    @GetMapping("/stat")
    public int getCount() {
        return articleService.getCountOfPublishedArticles();
    }
}
