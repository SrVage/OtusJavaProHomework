package http.server.enums;

public enum Headers {
    ACCEPT("Accept"),
    SET_COOKIE("Set-Cookie"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_DISPOSITION("Content-Disposition"),
    ETAG("ETag"),
    LAST_MODIFIED("Last-Modified"),
    IF_NONE_MATCH("If-None-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since");
    private final String header;

    Headers(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return header;
    }
}
