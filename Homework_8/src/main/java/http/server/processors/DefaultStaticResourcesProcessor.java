package http.server.processors;

import http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DefaultStaticResourcesProcessor implements RequestProcessor {
    private final Map<String, byte[]> cache = new HashMap<>();
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String filename = httpRequest.getUri().substring(1);
        if (cache.containsKey(filename)) {
            output.write(cache.get(filename));
            return;
        }
        Path filePath = Paths.get("Homework_8/static/", filename);
        String fileType = filename.substring(filename.lastIndexOf(".") + 1);
        byte[] fileData = Files.readAllBytes(filePath);

        String contentDisposition = "";
        if (fileType.equals("pdf")) {
            contentDisposition = "Content-Disposition: attachment;filename=" + filename + "\r\n";
        }

        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + fileData.length + "\r\n" +
                contentDisposition +
                "\r\n";

        byte[] cachedResponse = createCachedArray(response, fileData);

        cache.put(filename, cachedResponse);

        output.write(cachedResponse);
    }

    private static byte[] createCachedArray(String response, byte[] fileData) {
        byte[] responseBytes = response.getBytes();
        byte[] cachedResponse = new byte[responseBytes.length + fileData.length];
        System.arraycopy(responseBytes, 0, cachedResponse, 0, responseBytes.length);
        System.arraycopy(fileData, 0, cachedResponse, responseBytes.length, fileData.length);
        return cachedResponse;
    }
}
