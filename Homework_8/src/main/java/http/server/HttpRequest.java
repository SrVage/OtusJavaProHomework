package http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HttpRequest {
    private String rawRequest;
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private String body;
    private String sessionID;

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class.getName());

    public String getRouteKey() {
        return String.format("%s %s", method, uri);
    }

    public String getUri() {
        return uri;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getBody() {
        return body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHeader(String header) { return headers.get(header); }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parseRequestLine();
        this.tryToParseBody();
        this.getHeaders();
        this.getSessionId();

        logger.debug("\nSessionID: {}\n{}", sessionID, rawRequest);
        logger.trace("{} {}\nParameters: {}\nBody: {}", method, uri, parameters, body); // TODO правильно все поназывать
    }

    private void getSessionId() {
        var cookie = getHeader("Cookie");
        String session;
        if (cookie == null || cookie.isEmpty()){
            session = UUID.randomUUID().toString();
            sessionID = session;
            session+="SESSIONID="+session+";";
        } else {
            var cookieParams = parseCookie(cookie);
            var sessionKey = cookieParams.keySet().stream()
                    .filter(item->item.toUpperCase().contains("SESSIONID"))
                    .findFirst().orElse("SESSIONID");
            var sessionId = cookieParams.getOrDefault(sessionKey, "");
            if (sessionId.isEmpty()){
                sessionId = UUID.randomUUID().toString();
                cookieParams.put(sessionKey, sessionId);
            }
            sessionID = sessionId;
            session = cookieToString(cookieParams);
        }
        headers.put("Cookie", session);
        headers.put("Set-Cookie", sessionID);
    }

    private HashMap<String, String> parseCookie(String cookie) {
        HashMap<String, String> cookieParams = new HashMap<>();
        var cookies = cookie.split("; ");
        for (var param : cookies){
            var splitParams = param.split("=");
            if (splitParams.length>1){
                cookieParams.put(splitParams[0], splitParams[1]);
            }
        }
        return cookieParams;
    }

    private String cookieToString(HashMap<String, String> cookieMap){
        StringBuilder builder = new StringBuilder();
        for (var cookie:cookieMap.entrySet()){
            builder.append(cookie.getKey())
                    .append("=")
                    .append(cookie.getValue())
                    .append("; ");
        }
        return builder.toString();
    }

    public void tryToParseBody() {
        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            List<String> lines = rawRequest.lines().collect(Collectors.toList());
            int splitLine = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).isEmpty()) {
                    splitLine = i;
                    break;
                }
            }
            if (splitLine > -1) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = splitLine + 1; i < lines.size(); i++) {
                    stringBuilder.append(lines.get(i));
                }
                this.body = stringBuilder.toString();
            }
        }
    }

    public void parseRequestLine() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        this.parameters = new HashMap<>();
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            this.uri = elements[0];
            String[] keysValues = elements[1].split("&");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                this.parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    private void getHeaders() {
        this.headers = new HashMap<>();
        var lines = rawRequest.split("\n");
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isEmpty()){
                break;
            }
            String[] headerParts = lines[i].split(": ", 2);
            if (headerParts.length>1){
                headers.put(headerParts[0], headerParts[1]);
            }
        }
    }
}
