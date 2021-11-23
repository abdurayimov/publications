package name.lenmar.publications;

import name.lenmar.publications.controller.ArticleController;
import name.lenmar.publications.entity.Article;
import name.lenmar.publications.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PublicationsApplicationTests {

	@Autowired
	private ArticleRepository aRepo;

	@Autowired
	private ArticleController aContr;

	@Test
	public void contextLoads() {
		assertThat(aContr).isNotNull();
	}

	@Test
	public void testCreate() {
		Article article = Article.builder()
				.id(1L)
				.title("MacBook For Dummies")
				.author("Mark L. Chambers")
				.content("Make MacBook magic with this updated guide!")
				.dateOfPublishing(LocalDate.of(2021, 11, 15))
				.build();
		aRepo.save(article);
		Optional optional = aRepo.findById(1L);
		if (optional.isPresent())
			assertNotNull(optional.get());
	}

	@Test
	public void testCreateWithLongTitle() {
		Article article = Article.builder()
				.id(2L)
				.title("Long-Term Survival Advantage and Prognostic Factors Associated With " +
						"Intraperitoneal Chemotherapy Treatment in Advanced Ovarian Cancer: " +
						"A Gynecologic Oncology Group Study")
				.author("Devansu Tewari")
				.content("To determine long-term survival and associated prognostic factors after " +
						"intraperitoneal (IP) chemotherapy in patients with advanced ovarian cancer.")
				.dateOfPublishing(LocalDate.of(2022, 1, 2))
				.build();
		aRepo.save(article);
		assertThat(aRepo.findById(1L).get());
	}

}
