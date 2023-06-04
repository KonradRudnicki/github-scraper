package konradrudnicki.githubscraper.controllers;


import jakarta.servlet.http.HttpServletRequest;
import konradrudnicki.githubscraper.exceptions.CustomError;
import konradrudnicki.githubscraper.model.Repository;
import konradrudnicki.githubscraper.services.ScraperService;
import org.kohsuke.github.GHFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public List<Repository> getForkedRepos(@PathVariable String username,
                                           @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType) throws IOException {

        if (!contentType.equals("application/json")){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        return scraperService.getForkedRepos(username);
    }

    @ExceptionHandler(GHFileNotFoundException.class)
    @ResponseBody
    public ResponseEntity<CustomError> handleUserNotFoundException(GHFileNotFoundException ex) {
        CustomError error = new CustomError(HttpStatus.NOT_FOUND.value(), "User not found");

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<CustomError> handleResponseStatusException(ResponseStatusException ex) {
        CustomError error = new CustomError(HttpStatus.NOT_ACCEPTABLE.value(), "This API does not support the request content type.");

        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}
