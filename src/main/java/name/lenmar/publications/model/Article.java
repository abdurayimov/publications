package name.lenmar.publications.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor // temp
public class Article {

    @NotEmpty(message = "{validation.title.NotEmpty}")
    @Size(min = 2, max = 100, message = "{validation.title.Size}")
    private String title;

    @NotEmpty(message = "{validation.author.NotEmpty}")
    @Size(min = 2, message = "{validation.autor.Size}")
    private String author;

    @NotEmpty(message = "{validation.content.NotEmpty}")
    @Size(min = 2, message = "{validation.content.Size}")
    private String content;

    @NotNull(message = "{validation.dateOfPublishing.NotNull}")
    @Past(message = "{validation.dateOfPublishing.Past}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublishing;
}
