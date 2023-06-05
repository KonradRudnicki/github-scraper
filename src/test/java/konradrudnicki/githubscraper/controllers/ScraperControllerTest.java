package konradrudnicki.githubscraper.controllers;

import konradrudnicki.githubscraper.model.Repository;
import konradrudnicki.githubscraper.services.ScraperService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.kohsuke.github.GHFileNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class ScraperControllerTest {

    @Mock
    private ScraperService scraperService;

    @InjectMocks
    private ScraperController scraperController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(scraperController).build();
    }


    @Test
    public void getForkedReposTest() throws Exception {
        String username = "user";
        List<Repository> repos = new ArrayList<>();
        when(scraperService.getForkedRepos(username)).thenReturn(repos);

        mockMvc.perform(get("/repos/" + username)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getForkedReposUserNotFoundTest() throws Exception {
        String username = "user";
        when(scraperService.getForkedRepos(username)).thenThrow(GHFileNotFoundException.class);

        mockMvc.perform(get("/repos/" + username)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getForkedReposInvalidContentTypeTest() throws Exception {
        String username = "user";
        mockMvc.perform(get("/repos/" + username)
                        .header("Content-Type", "application/xml"))
                .andExpect(status().isNotAcceptable());
    }
}