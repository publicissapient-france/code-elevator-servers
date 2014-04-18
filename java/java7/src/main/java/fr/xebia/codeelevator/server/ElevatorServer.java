package fr.xebia.codeelevator.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fr.xebia.codeelevator.Direction;
import fr.xebia.codeelevator.ElevatorEngine;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

class ElevatorServer implements HttpHandler {
    private ElevatorEngine elevator;

    ElevatorServer(ElevatorEngine elevatorEngine) {
        this.elevator = elevatorEngine;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI requestURI = httpExchange.getRequestURI();
        Map<String, String> parameters = extractParameters(requestURI);
        switch (requestURI.getPath()) {
            case "/reset":
                elevator.reset(parameters.get("cause"));
                httpExchange.sendResponseHeaders(200, 0);
                break;
            case "/call":
                elevator.call(parseInt(parameters.get("atFloor")), Direction.valueOf(parameters.get("to")));
                httpExchange.sendResponseHeaders(200, 0);
                break;
            case "/go":
                elevator.go(parseInt(parameters.get("floorToGo")));
                httpExchange.sendResponseHeaders(200, 0);
                break;
            case "/nextCommand":
                String nextCommand = elevator.nextCommand().toString();
                httpExchange.sendResponseHeaders(200, nextCommand.length());
                try (Writer out = new OutputStreamWriter(httpExchange.getResponseBody())) {
                    out.write(nextCommand);
                }
                break;
            case "/userHasEntered":
                elevator.userHasEntered();
                httpExchange.sendResponseHeaders(200, 0);
                break;
            case "/userHasExited":
                elevator.userHasExited();
                httpExchange.sendResponseHeaders(200, 0);
                break;
        }
        httpExchange.close();
    }

    public static void main(String[] args) throws IOException {
        final HttpServer httpServer = HttpServer.create(new InetSocketAddress(8081), 0);
        httpServer.createContext("/", new ElevatorServer(new ElevatorEngine()));
        httpServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                httpServer.stop(1);
            }
        }));
    }

    private static Map<String, String> extractParameters(URI uri) {
        String query = uri.getQuery();
        Map<String, String> parametersAndValues = new HashMap<>();
        if (query == null) {
            return parametersAndValues;
        }
        Scanner scanner = new Scanner(query).useDelimiter("&");
        while (scanner.hasNext()) {
            String[] parameterAndValue = scanner.next().split("=");
            parametersAndValues.put(parameterAndValue[0], parameterAndValue[1].replace('+', ' '));
        }
        return parametersAndValues;
    }
}