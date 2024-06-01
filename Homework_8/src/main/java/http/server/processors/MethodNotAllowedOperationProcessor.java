package http.server.processors;

import http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class MethodNotAllowedOperationProcessor{

    public void execute(HttpRequest httpRequest, OutputStream output, Set<String> routers) throws IOException {
        var methodName = httpRequest.getRouteKey().substring(httpRequest.getRouteKey().indexOf("/"));

        var correctMethod = routers.stream().filter(item->item.contains(methodName)).findFirst();
        String response;
        if (correctMethod.isPresent()){
            response = "HTTP/1.1 405 Method Not Allowed\r\nContent-Type: text/html\r\n\r\n<html><body><h1>Method Not Allowed!!!</h1></body></html>";
        } else {
            response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<html><body><h1>UNKNOWN OPERATION REQUEST!!!</h1></body></html>";
        }
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
