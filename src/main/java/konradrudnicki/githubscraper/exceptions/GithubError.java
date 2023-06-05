package konradrudnicki.githubscraper.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GithubError {

    private int status;
    private String message;
}