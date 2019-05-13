package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RootHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Path path;
        OutputStream outputStream;

        String isValidURI = exchange.getRequestURI().toString();

        if(isValidURI.equals("/") || isValidURI.equals("/index.html")){
            path = Paths.get("/Users/saejongjang/Downloads/familyserver/server/src/web/index.html");
        }
        else if(isValidURI.equals("/css/main.css")){
            path = Paths.get("/Users/saejongjang/Downloads/familyserver/server/src/web/css/main.css");
        }
        else{
            path = Paths.get("/Users/saejongjang/Downloads/familyserver/server/src/web/HTML/404.html");
        }

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        outputStream = exchange.getResponseBody();
        Files.copy(path, outputStream);
        outputStream.close();
    }


}
