package name.lenmar.publications.error;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(Long id) {
        super("Article id not found: " + id);
    }
}
