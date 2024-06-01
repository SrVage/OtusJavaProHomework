package http.server.application.processors;

import http.server.HttpRequest;
import http.server.helpers.CheckAcceptHeader;
import http.server.processors.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalculatorRequestProcessor implements RequestProcessor {
    private static final Logger log = LoggerFactory.getLogger(CalculatorRequestProcessor.class);
    private static final String acceptHeader = "text/html";

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        if (!CheckAcceptHeader.checkAccept(httpRequest, output, acceptHeader)){
            return;
        }
        int a = Integer.parseInt(httpRequest.getParameter("a"));
        int b = Integer.parseInt(httpRequest.getParameter("b"));
        int result = a + b;
        String outMessage = a + " + " + b + " = " + result;

        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nSet-Cookie: "+httpRequest.getHeader("Set-Cookie")+"\r\n\r\n<html><body><h1>" + outMessage + "</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
