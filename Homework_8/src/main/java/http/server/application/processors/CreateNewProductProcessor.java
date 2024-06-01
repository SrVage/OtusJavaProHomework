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

public class CreateNewProductProcessor implements RequestProcessor {
    private static final String acceptHeader = "application/json";

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        if (!CheckAcceptHeader.checkAccept(httpRequest, output, acceptHeader)){
            return;
        }
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        Storage.save(item);
        String jsonOutItem = gson.toJson(item);

        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Set-Cookie: " + httpRequest.getHeader("Set-Cookie") +
                "Connection: keep-alive\r\n" +
                "Access-Control-Allow-Origin: *\r\n" +
                "\r\n" + jsonOutItem;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
