package http.server.application.processors;

import com.google.gson.Gson;
import http.server.HttpRequest;
import http.server.application.Item;
import http.server.application.Storage;
import http.server.enums.CodeMessage;
import http.server.enums.Headers;
import http.server.helpers.CheckAcceptHeader;
import http.server.processors.AcceptProcessor;
import http.server.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewProductProcessor implements RequestProcessor, AcceptProcessor {
    private static final String acceptHeader = "application/json";

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        Storage.save(item);
        String jsonOutItem = gson.toJson(item);

        String response = String.format("HTTP/1.1 %s\r\n" +
                "Content-Type: application/json\r\n" +
                "Set-Cookie: %s" +
                "Connection: keep-alive\r\n" +
                "Access-Control-Allow-Origin: *\r\n" +
                "\r\n%s",
                CodeMessage.OK, httpRequest.getHeader(Headers.SET_COOKIE.getHeader()), jsonOutItem);
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String getAccept() {
        return acceptHeader;
    }
}
