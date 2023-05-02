package FileHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    public FileHandler(){}


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow GET requests for this operation.
            // This operation requires a GET request, because the
            // client is "getting" information from the server, and
            // the operation is "read only" (i.e., does not modify the
            // state of the server).
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String URLPath = exchange.getRequestURI().toString();

                if(URLPath.equals( null) || URLPath.equals("index.html") || URLPath.equals("/"))
                {
                    URLPath = "/index.html";
                    System.out.println("/index.html");

                }

                if(URLPath.equals("/favicon.ico"))
                {
                    URLPath = "/favicon.ico";
                    System.out.println("/favicon.ico");
                }

                if(URLPath.equals("/css/main.css"))
                {
                    URLPath = "/css/main.css";
                    System.out.println("/css/main.css");


                }








                String filePath = "web" + URLPath;



                File webServer = new File(filePath);

                //System.out.println(webServer.canRead());
                if(webServer.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                   // System.out.println(webServer.toPath().toString());
                    Files.copy(webServer.toPath(),respBody);
                    respBody.close();

                    success = true;
                }




            }

            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                // Since the client request was invalid, they will not receive the
                // list of games, so we close the response body output stream,
                // indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {

            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }


    }

