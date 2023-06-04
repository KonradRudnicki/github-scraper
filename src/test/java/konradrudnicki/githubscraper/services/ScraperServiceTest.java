package konradrudnicki.githubscraper.services;

import konradrudnicki.githubscraper.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScraperServiceTest {

    private ScraperService scraperService;
    private GitHub gitHub;

    @BeforeEach
    public void setup() throws IOException {
        this.scraperService = new ScraperService();
        this.gitHub = mock(GitHub.class);
    }

    @Test
    public void testGetForkedRepos() throws IOException {
        // Given
        String username = "testUser";

        GHUser user = mock(GHUser.class);
        GHRepository repo = mock(GHRepository.class);
        GHBranch branch = mock(GHBranch.class);

        Map<String, GHRepository> repos = new HashMap<>();
        repos.put("testRepo", repo);

        when(gitHub.getUser(any(String.class))).thenReturn(user);
        when(user.getRepositories()).thenReturn(repos);
        when(repo.isFork()).thenReturn(false);
        when(repo.getName()).thenReturn("testRepo");
        when(repo.getOwnerName()).thenReturn(username);
        when(repo.getBranches()).thenReturn(Collections.singletonMap("master", branch));
        when(branch.getName()).thenReturn("master");
        when(branch.getSHA1()).thenReturn("abc123");

        // When
        List<Repository> result = scraperService.getForkedRepos(username);

        // Then
        assertEquals(1, result.size());
        assertEquals("testRepo", result.get(0).getName());
        assertEquals(username, result.get(0).getOwner());
        assertEquals(1, result.get(0).getBranches().size());
        assertEquals("master", result.get(0).getBranches().get(0).getName());
        assertEquals("abc123", result.get(0).getBranches().get(0).getSha());
    }
}
