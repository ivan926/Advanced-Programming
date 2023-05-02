package FileHandler;

import Request_Response.LoginRequest;
import Request_Response.LoginResponse;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

public class LoginHandler extends Handler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {




        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();


                // Get the request body input stream
                        InputStream reqBody = exchange.getRequestBody();

                        // Read JSON string from the input stream
                        String reqData = readString(reqBody);

                        // Display/log the request JSON data
                        //System.out.println(reqData);




                        Gson gson = new Gson();

                        LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);

						LoginService service = new LoginService();

						LoginResponse result = service.login(request);

                        String jsonStringResponse = gson.toJson(result);
                        if(result.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
						OutputStream resBody = exchange.getResponseBody();

						writeString(jsonStringResponse,resBody);

					 	resBody.close();


                        success = true;
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