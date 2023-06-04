package konradrudnicki.githubscraper.exceptions;

public class CustomError {

    private int status;
    private String message;

    // Constructor, Getters and Setters

    public CustomError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and Setters

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
