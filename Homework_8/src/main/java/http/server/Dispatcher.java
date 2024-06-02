package http.server;

import http.server.application.processors.CalculatorRequestProcessor;
import http.server.application.processors.CreateNewProductProcessor;
import http.server.application.processors.GetAllProductsProcessor;
import http.server.application.processors.HelloWorldRequestProcessor;
import http.server.helpers.CheckAcceptHeader;
import http.server.processors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> router;
    private Map<String, AcceptProcessor> acceptProcessorMap;
    private RequestProcessor unknownOperationRequestProcessor;
    private MethodNotAllowedOperationProcessor methodNotAllowedOperationProcessor;
    private RequestProcessor optionsRequestProcessor;
    private RequestProcessor staticResourcesProcessor;

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class.getName());

    public Dispatcher() {
        this.router = new HashMap<>();
        this.acceptProcessorMap = new HashMap<>();
        this.router.put("GET /calc", new CalculatorRequestProcessor());
        this.router.put("GET /hello", new HelloWorldRequestProcessor());
        this.router.put("GET /items", new GetAllProductsProcessor());
        this.router.put("POST /items", new CreateNewProductProcessor());

        this.unknownOperationRequestProcessor = new DefaultUnknownOperationProcessor();
        this.optionsRequestProcessor = new DefaultOptionsProcessor();
        this.staticResourcesProcessor = new DefaultStaticResourcesProcessor();
        this.methodNotAllowedOperationProcessor = new MethodNotAllowedOperationProcessor();

        addProcessorInAcceptGroup();

        logger.info("Диспетчер проинициализирован");
    }

    private void addProcessorInAcceptGroup() {
        for (var processor:router.entrySet()){
            if (processor.getValue() instanceof AcceptProcessor acceptProcessor){
                acceptProcessorMap.put(processor.getKey(), acceptProcessor);
            }
        }
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream) throws IOException {
        if (httpRequest.getMethod() == HttpMethod.OPTIONS) {
            optionsRequestProcessor.execute(httpRequest, outputStream);
            return;
        }
        if (Files.exists(Paths.get("Homework_8/static/", httpRequest.getUri().substring(1)))) {
            staticResourcesProcessor.execute(httpRequest, outputStream);
            return;
        }
        if (!router.containsKey(httpRequest.getRouteKey())) {
            methodNotAllowedOperationProcessor.execute(httpRequest, outputStream, router.keySet());
            return;
        }
        if (acceptProcessorMap.containsKey(httpRequest.getRouteKey())){
            var acceptHeader = acceptProcessorMap.get(httpRequest.getRouteKey()).getAccept();
            if (!CheckAcceptHeader.checkAccept(httpRequest, outputStream, acceptHeader)){
                return;
            }
        }
        router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream);
    }
}
