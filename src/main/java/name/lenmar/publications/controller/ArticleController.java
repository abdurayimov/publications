package name.lenmar.publications.controller;

import name.lenmar.publications.model.Article;
import name.lenmar.publications.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Page<Article> getAllArticles(@RequestParam Integer page, @RequestParam Integer size) {
        return articleService.getAll( PageRequest.of(page, size) );
    }

    @GetMapping("{id}")
    public Article getArticleById(@PathVariable Long id) {
        return articleService.get(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Transactional
    public Article createArticle(@RequestBody @Valid Article article) {
        return articleService.create(article);
    }

    @PutMapping("{id}")
    @Transactional
    public Article editArticleById(@PathVariable Long id, @RequestBody @Valid Article article) {
        return articleService.update(id, article);
    }

    @DeleteMapping("{id}")
    public void deleteArticleById(@PathVariable Long id) {
        articleService.delete(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
