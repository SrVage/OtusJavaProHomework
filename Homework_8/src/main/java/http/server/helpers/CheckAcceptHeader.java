package http.server.helpers;


import http.server.HttpRequest;
import http.server.enums.CodeMessage;
import http.server.enums.Headers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CheckAcceptHeader {

    public static boolean checkAccept(HttpRequest httpRequest, OutputStream output, String header) throws IOException {
        var accept = httpRequest.getHeader(Headers.ACCEPT.getHeader());
        if (!accept.contains(header)){
            String response = String.format("HTTP/1.1 {}\r\nContent-Type: {}\r\n\r\n", CodeMessage.NOT_ACCEPTABLE, header);
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return false;
        }
        return true;
    }
}
