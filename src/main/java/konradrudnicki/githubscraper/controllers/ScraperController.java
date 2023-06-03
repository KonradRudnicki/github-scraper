package konradrudnicki.githubscraper.controllers;


import konradrudnicki.githubscraper.model.Repository;
import konradrudnicki.githubscraper.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ScraperController {

    ScraperService scraperService;

    @Autowired
    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/repos/{username}")
    public List<Repository> getForkedRepos(@PathVariable String username) throws IOException {

        return scraperService.getForkedRepos(username);
    }
}