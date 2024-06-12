package http.server.enums;

public enum CodeMessage {
    NOT_ACCEPTABLE("406 Not Acceptable"),
    OK("200 OK"),
    NOT_MODIFIED("304 Not Modified");

    private final String message;

    CodeMessage(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }

    @Override
    public String toString() {
        return message;
    }
}
