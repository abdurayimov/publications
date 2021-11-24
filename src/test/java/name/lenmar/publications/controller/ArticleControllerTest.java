package name.lenmar.publications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.lenmar.publications.entity.Article;
import name.lenmar.publications.repository.ArticleRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
//import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@Testcontainers
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticleControllerTest {

    @Autowired
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleRepository mockRepository;

    @WithMockUser("USER")
    @Test
    void createArticle() throws Exception {
        Article testArticle = mockArticle();
        String json = om.writeValueAsString(testArticle);
        MvcResult mvcResult = mockMvc.perform(post("/article").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        Article articleResult = om.readValue(mvcResult.getResponse().getContentAsString(), Article.class);
        assertThat(articleResult.getId(), notNullValue());
        assertThat(articleResult.getTitle(), equalTo(testArticle.getTitle()));
        assertThat(articleResult.getAuthor(), equalTo(testArticle.getAuthor()));
        assertThat(articleResult.getContent(), equalTo(testArticle.getContent()));
        assertThat(articleResult.getDateOfPublishing(), equalTo(testArticle.getDateOfPublishing()));
    }

    @WithMockUser("USER")
    @Test
    public void findArticleById999() throws Exception {
        init();
        mockMvc.perform(get("/article/999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(999)))
                .andExpect(jsonPath("$.title", is("A Guide to the Bodhisattva Way of Life")))
                .andExpect(jsonPath("$.author", is("Santideva")))
                .andExpect(jsonPath("$.content", is("test content")))
                .andExpect(jsonPath("$.dateOfPublishing", is("2021-12-15")));
    }

    /*{
        "timestamp":"2021-11-24T14:21:53.964+00:00",
        "status": 400,
        errors": [
            "Please provide a Date of publishing",
            "Title size must be between 2 and 100",
            "Author size must be at least 2",
            "Please provide a Author",
            "Please provide a Title"
        ]
    }*/
    @WithMockUser("USER")
    @Test
    public void save_emptyTitle_emptyAuthor_emptyDate_400() throws Exception {

        String articleInJson = "{\"title\":\"\",\"author\":\"\",\"content\":\"quam sapien varius ut blandit non interdum in ante vestibulum\",\"dateOfPublishing\":\"\"}";

        mockMvc.perform(post("/article")
                        .content(articleInJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(5)))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a Date of publishing")))
                .andExpect(jsonPath("$.errors", hasItem("Title size must be between 2 and 100")))
                .andExpect(jsonPath("$.errors", hasItem("Author size must be at least 2")))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a Author")))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a Title")));

        //verify(mockRepository, times(0)).save(any(Article.class));
    }

    /*{
        "timestamp": "2021-11-24T15:36:48.634+00:00",
        "status": 400,
        "errors": [
            "Title size must be between 2 and 100"
        ]
    }*/

    @WithMockUser("USER")
    @Test
    public void save_longTitleMax100_400() throws Exception {
        String articleInJson = "{\"title\":\"Make MacBook magic with this updated guide! Make MacBook magic with this updated guide! Make MacBook magic with this updated guide!\",\"author\":\"Daphene McAloren\",\"content\":\"neque aenean auctor gravida sem praesent id massa id nisl venenatis lacinia aenean sit amet justo morbi\",\"dateOfPublishing\":\"2021-12-24\"}";

        mockMvc.perform(post("/article")
                        .content(articleInJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Title size must be between 2 and 100")));

        //verify(mockRepository, times(0)).save(any(Article.class));
    }

    /* Article id not found: 666 */

    @WithMockUser("USER")
    @Test
    public void getNotExistingArticleTest() throws Exception {
        mockMvc.perform(get("/article/666"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /* return number of published articles on daily basis for the 7 days
       For testing from date of 2021-11-24 there are 59 rows are found
     */

    @WithMockUser(username = "ADMIN",
            authorities = "ROLE_ADMIN")
    @Test
    public void getCountOfPublishedArticles() throws Exception {
        mockMvc.perform(get("/stat"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("59"));
    }

    private Article mockArticle() {
        return Article.builder()
                .title("MacBook For Dummies")
                .author("Mark L. Chambers")
                .content("Make MacBook magic with this updated guide!")
                .dateOfPublishing(LocalDate.of(2021, 11, 15))
                .build();
    }

    private void init() {
        Article article = new Article(999L, "A Guide to the Bodhisattva Way of Life", "Santideva", "test content", LocalDate.of(2021, 12, 15));
        when(mockRepository.findById(999L)).thenReturn(Optional.of(article));
    }
}