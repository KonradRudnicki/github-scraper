package konradrudnicki.githubscraper.services;

import konradrudnicki.githubscraper.model.Branch;
import konradrudnicki.githubscraper.model.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScraperServiceTest {

    @Mock
    private GitHub gitHub;

    @Mock
    private GHUser ghUser;

    @Mock
    private GHRepository ghRepository;

    @Mock
    private GHBranch ghBranch;

    @InjectMocks
    private ScraperService scraperService;

    @BeforeEach
    public void setUp() throws IOException {
        when(gitHub.getUser(anyString())).thenReturn(ghUser);

        Map<String, GHRepository> repos = new HashMap<>();
        repos.put("repo", ghRepository);
        when(ghUser.getRepositories()).thenReturn(repos);

        Map<String, GHBranch> branches = new HashMap<>();
        branches.put("branch", ghBranch);
        when(ghRepository.getBranches()).thenReturn(branches);

        when(ghRepository.isFork()).thenReturn(false);
        when(ghRepository.getName()).thenReturn("repo");
        when(ghRepository.getOwnerName()).thenReturn("user");

        when(ghBranch.getName()).thenReturn("branch");
        when(ghBranch.getSHA1()).thenReturn("sha");
    }

    @Test
    public void testGetForkedRepos() throws IOException {
        List<Repository> repos = scraperService.getForkedRepos("user");

        assertEquals(1, repos.size());
        Repository repo = repos.get(0);
        assertEquals("repo", repo.getName());
        assertEquals("user", repo.getOwner());

        List<Branch> repoBranches = repo.getBranches();
        assertEquals(1, repoBranches.size());

        Branch branch = repoBranches.get(0);
        assertEquals("branch", branch.getName());
        assertEquals("sha", branch.getSha());
    }
}