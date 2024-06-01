package http.server.application.processors;

import http.server.HttpRequest;
import http.server.helpers.CheckAcceptHeader;
import http.server.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HelloWorldRequestProcessor implements RequestProcessor {
    private static final String acceptHeader = "text/html";

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        if (!CheckAcceptHeader.checkAccept(httpRequest, output, acceptHeader)){
            return;
        }
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nSet-Cookie: "+httpRequest.getHeader("Set-Cookie")+"\r\n\r\n<html><body><h1>Hello World!!!</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
