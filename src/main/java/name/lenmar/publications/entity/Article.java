package name.lenmar.publications.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{validation.title.NotEmpty}")
    @Size(min = 2, max = 100, message = "{validation.title.Size}")
    private String title;

    @NotEmpty(message = "{validation.author.NotEmpty}")
    @Size(min = 2, message = "{validation.author.Size}")
    private String author;

    @NotEmpty(message = "{validation.content.NotEmpty}")
    @Size(min = 2, message = "{validation.content.Size}")
    private String content;

    @NotNull(message = "{validation.dateOfPublishing.NotNull}")
    @Past(message = "{validation.dateOfPublishing.Past}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfPublishing;
}
