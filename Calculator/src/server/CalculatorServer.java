package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Calculation;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class starts a small HTTP server that serves the calculator webpage
 * and connects its JavaScript requests to the Java calculation model.
 *
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
public final class CalculatorServer {

    /**
     * The local port used by the calculator server.
     */
    private static final int PORT = 8080;

    /**
     * The largest expression accepted by the API.
     */
    private static final int MAX_EXPRESSION_LENGTH = 200;

    /**
     * The directory containing the webpage files.
     */
    private static final Path WEB_DIRECTORY =
            Path.of("web").toAbsolutePath().normalize();

    /**
     * Prevents construction of this utility class.
     */
    private CalculatorServer() {
    }

    /**
     * Starts the calculator server.
     *
     * @param args command-line arguments; they are not used
     * @throws IOException if the server can not start
     */
    public static void main(final String[] args) throws IOException {
        Calculation calculation = new Calculation();
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/api/calculate",
                exchange -> handleCalculation(exchange, calculation));
        server.createContext("/", CalculatorServer::handleStaticFile);
        server.setExecutor(null);
        server.start();

        System.out.println("Calculator running at http://localhost:" + PORT);
    }

    /**
     * Calculates an expression received in a POST request.
     *
     * @param exchange the current HTTP request and response
     * @param calculation the calculator model
     * @throws IOException if the request or response can not be processed
     */
    private static void handleCalculation(final HttpExchange exchange,
                                          final Calculation calculation)
            throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.getResponseHeaders().set("Allow", "POST");
            sendResponse(exchange, 405, "Only POST is allowed.", "text/plain");
            return;
        }

        byte[] requestBytes = exchange.getRequestBody()
                .readNBytes(MAX_EXPRESSION_LENGTH + 1);
        if (requestBytes.length > MAX_EXPRESSION_LENGTH) {
            sendResponse(exchange, 413, "Expression is too long.", "text/plain");
            return;
        }

        String expression = new String(requestBytes, StandardCharsets.UTF_8);

        try {
            double result = calculation.calculate(expression);
            sendResponse(exchange, 200, formatResult(result), "text/plain");
        } catch (IllegalArgumentException | ArithmeticException exception) {
            sendResponse(exchange, 400, exception.getMessage(), "text/plain");
        }
    }

    /**
     * Serves one of the calculator's static webpage files.
     *
     * @param exchange the current HTTP request and response
     * @throws IOException if the file or response can not be processed
     */
    private static void handleStaticFile(final HttpExchange exchange)
            throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.getResponseHeaders().set("Allow", "GET");
            sendResponse(exchange, 405, "Only GET is allowed.", "text/plain");
            return;
        }

        String requestedPath = exchange.getRequestURI().getPath();
        String fileName;

        switch (requestedPath) {
            case "/":
            case "/index.html":
                fileName = "index.html";
                break;
            case "/calculator.js":
                fileName = "calculator.js";
                break;
            case "/style.css":
                fileName = "style.css";
                break;
            default:
                sendResponse(exchange, 404, "File not found.", "text/plain");
                return;
        }

        Path file = WEB_DIRECTORY.resolve(fileName).normalize();
        if (!Files.isRegularFile(file)) {
            sendResponse(exchange, 404, "File not found.", "text/plain");
            return;
        }

        byte[] response = Files.readAllBytes(file);
        exchange.getResponseHeaders().set("Content-Type", contentType(fileName));
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    /**
     * Formats whole-number results without a decimal point.
     *
     * @param result the result to format
     * @return the formatted result
     */
    private static String formatResult(final double result) {
        if (result == Math.rint(result)
                && result >= Long.MIN_VALUE && result <= Long.MAX_VALUE) {
            return Long.toString((long) result);
        }
        return Double.toString(result);
    }

    /**
     * Returns the correct content type for a static file.
     *
     * @param fileName the name of the file
     * @return the file's content type
     */
    private static String contentType(final String fileName) {
        if (fileName.endsWith(".html")) {
            return "text/html; charset=utf-8";
        }
        if (fileName.endsWith(".css")) {
            return "text/css; charset=utf-8";
        }
        return "text/javascript; charset=utf-8";
    }

    /**
     * Sends a text response and closes the HTTP exchange.
     *
     * @param exchange the current HTTP request and response
     * @param statusCode the HTTP status code
     * @param message the response text
     * @param contentType the response content type
     * @throws IOException if the response can not be sent
     */
    private static void sendResponse(final HttpExchange exchange,
                                     final int statusCode,
                                     final String message,
                                     final String contentType)
            throws IOException {
        byte[] response = message.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set(
                "Content-Type", contentType + "; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }
}
