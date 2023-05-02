package FileHandler;

import Request_Response.ClearRequest;
import Request_Response.ClearResponse;
import Request_Response.RegisterRequest;
import Request_Response.RegisterResponse;
import Service.ClearService;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

public class ClearHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            System.out.println("Beginning the wipe of the database");

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                //creating gson object
                Gson gson = new Gson();

                ClearRequest request = new ClearRequest();


                ClearService service = new ClearService();
                ClearResponse result = service.clear(request);

                String JSON_ResponseBody = gson.toJson(result);

                if(result.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    System.out.println(JSON_ResponseBody);
                    writeString(JSON_ResponseBody, resBody);


                    resBody.close();


                    success = true;
                }
                else{

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    System.out.println(JSON_ResponseBody);
                    writeString(JSON_ResponseBody, resBody);


                    resBody.close();


                    success = true;

                }
            }

            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }


    }
}
