package FileHandler;

import Request_Response.LoadRequest;
import Request_Response.LoadResponse;
import Request_Response.LoginRequest;
import Request_Response.LoginResponse;
import Service.LoadService;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
               // System.out.println("We are inside the load handler before printing the req Body");
                System.out.println(reqData);




                Gson gson = new Gson();

//                try
//                {
//                    LoadRequest request = (LoadRequest)gson.fromJson(reqData, LoadRequest.class);
//                    request.printArrays();
//                }
//                catch(Exception exception)
//                {
//                   System.out.println( exception.getMessage());
//                   LoadResponse loadResponse = new LoadResponse("Error: Missing property",false);
//                    String jsonStringResponse = gson.toJson(loadResponse);
//                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                    OutputStream resBody = exchange.getResponseBody();
//
//                    writeString(jsonStringResponse,resBody);
//
//                    resBody.close();
//                    System.out.println("inside catch");
//                    return;
//                }


                LoadRequest request = (LoadRequest)gson.fromJson(reqData, LoadRequest.class);
                request.printArrays();

                LoadService service = new LoadService();

                LoadResponse result = service.load(request);

                String jsonStringResponse = gson.toJson(result);
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();

//                writeString(jsonStringResponse,resBody);



                if(result.getSuccess() == false)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    writeString(jsonStringResponse,resBody);
                    resBody.close();

                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    writeString(jsonStringResponse,resBody);
                    resBody.close();
                }
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
