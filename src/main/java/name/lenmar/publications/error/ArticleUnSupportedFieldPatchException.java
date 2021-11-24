package name.lenmar.publications.error;

import java.util.Set;

public class ArticleUnSupportedFieldPatchException extends RuntimeException {

    public ArticleUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }

}
