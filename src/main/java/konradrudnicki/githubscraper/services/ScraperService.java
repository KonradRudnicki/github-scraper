package konradrudnicki.githubscraper.services;

import konradrudnicki.githubscraper.model.Branch;
import konradrudnicki.githubscraper.model.Repository;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScraperService {

    private final GitHub gitHub;

    @Autowired
    public ScraperService(GitHub gitHub) {
        this.gitHub = gitHub;
    }

    public List<Repository> getForkedRepos(String username) throws IOException {

        List<Repository> result = new ArrayList<>();
        GHUser user = gitHub.getUser(username);
        Map<String, GHRepository> repos = user.getRepositories();

        for (Map.Entry<String, GHRepository> repo : repos.entrySet()) {
            if (!repo.getValue().isFork()) {
                Repository curRepo = new Repository();
                curRepo.setName(repo.getValue().getName());
                curRepo.setOwner(repo.getValue().getOwnerName());
                curRepo.setBranches(
                        repo.getValue().getBranches().values().stream()
                                .map(branch -> {
                                    Branch b = new Branch();
                                    b.setName(branch.getName());
                                    b.setSha(branch.getSHA1());
                                    return b;
                                })
                                .collect(Collectors.toList()
                ));

                result.add(curRepo);
            }
        }

        return result;
    }
}