package name.lenmar.publications.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.lenmar.publications.model.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createArticle() throws Exception {
        Article testArticle = mockArticle();
        String json = objectMapper.writeValueAsString(testArticle);
        MvcResult mvcResult = mockMvc.perform(post("/article").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        Article articleResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Article.class);
        assertThat(articleResult.getId(), notNullValue());
        assertThat(articleResult.getTitle(), equalTo(testArticle.getTitle()));
        assertThat(articleResult.getAuthor(), equalTo(testArticle.getAuthor()));
        assertThat(articleResult.getContent(), equalTo(testArticle.getContent()));
        assertThat(articleResult.getDateOfPublishing(), equalTo(testArticle.getDateOfPublishing()));
    }

    @Test
    void findArticle() throws Exception {
        Article testArticle = mockArticle();
        String json = objectMapper.writeValueAsString(testArticle);

        mockMvc.perform(post("/article").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(get("/article").param("page", "0").param("size", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    private Article mockArticle() {
        return Article.builder()
                .title("MacBook For Dummies")
                .author("Mark L. Chambers")
                .content("Make MacBook magic with this updated guide!")
                .dateOfPublishing(LocalDate.of(2021, 11, 15))
                .build();
    }
}