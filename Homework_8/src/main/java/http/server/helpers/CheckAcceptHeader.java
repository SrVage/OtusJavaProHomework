package http.server.helpers;


import http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CheckAcceptHeader {

    public static boolean checkAccept(HttpRequest httpRequest, OutputStream output, String header) throws IOException {
        var accept = httpRequest.getHeader("Accept");
        if (!accept.contains(header)){
            String response = "HTTP/1.1 406 Not Acceptable\r\nContent-Type:" +header +"\r\n\r\n";
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return false;
        }
        return true;
    }
}
