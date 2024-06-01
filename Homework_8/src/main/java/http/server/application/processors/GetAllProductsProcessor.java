package http.server.application.processors;

import com.google.gson.Gson;
import http.server.HttpRequest;
import http.server.application.Item;
import http.server.application.Storage;
import http.server.helpers.CheckAcceptHeader;
import http.server.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetAllProductsProcessor implements RequestProcessor {
    private static final String acceptHeader = "application/json";

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        if (!CheckAcceptHeader.checkAccept(httpRequest, output, acceptHeader)){
            return;
        }
        List<Item> items = Storage.getItems();
        Gson gson = new Gson();
        String result = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Set-Cookie: " + httpRequest.getHeader("Set-Cookie") +
                "Connection: keep-alive\r\n" +
                "Access-Control-Allow-Origin: *\r\n\r\n" + gson.toJson(items);
        output.write(result.getBytes(StandardCharsets.UTF_8));
        output.flush();
    }
}
