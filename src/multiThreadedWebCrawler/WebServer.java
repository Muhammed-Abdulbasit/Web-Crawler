package multiThreadedWebCrawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.util.ArrayList;

public class WebServer {
    private static ArrayList<String> receivedLinks = new ArrayList<String>();

    public static void startPage() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server is running on port 8080");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String requestBody;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(t.getRequestBody()))) {
                requestBody = reader.lines().collect(Collectors.joining("\n"));
            }

            String link = requestBody;

            if (link != null && !link.isEmpty()){
                receivedLinks.add(link);
            }

            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append("<html><head><meta http-equiv=\"refresh\" content=\"3\"></head><body>");
            responseBuilder.append("<h1>Multi-Threaded Web Crawler</h1>");
            responseBuilder.append("<p>Received ").append(receivedLinks.size()).append(" links</p>");
            responseBuilder.append("<p>Received links:</p>");
            responseBuilder.append("<ul>");
            
            for (String receivedLink : receivedLinks){
                responseBuilder.append("<li>").append(receivedLink).append("</li>");
            }            
            responseBuilder.append("</ul></body></html>");
            
            String response = responseBuilder.toString();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}