package FileHandler;

import Request_Response.FillRequest;
import Request_Response.FillResponse;
import Request_Response.RegisterRequest;
import Request_Response.RegisterResponse;
import Service.FillService;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

public class FillHandler extends Handler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {


        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                Headers reqHeaders = exchange.getRequestHeaders();

                URI uri = exchange.getRequestURI();

                String path = uri.getPath();
                String firstParameter = null;
                int secondParameter = 0;
                int characterCount = 0;

                char[] CharpathArray = path.toCharArray();
                StringBuilder param1 = new StringBuilder();
                StringBuilder param2 = new StringBuilder();

                for(char currentLetter : CharpathArray)
                {
                    if(characterCount == 2 )
                    {
                        if(currentLetter != '/')
                        {
                            param1.append(currentLetter);
                        }
                    }

                    if(characterCount == 3)
                    {
                        param2.append(currentLetter);
                    }

                    if(Character.compare(currentLetter,'/') == 0 )
                    {

                        characterCount++;

                    }



                }

                firstParameter = param1.toString();
                if( param2.length() != 0) {
                    secondParameter = Integer.parseInt(param2.toString());


                }


                System.out.println(firstParameter);
                System.out.println(secondParameter);



                    //creating gson object
                    Gson gson = new Gson();
                FillRequest fillRequest = null;

                //determine whether an optional generation count was given
                if(secondParameter == 0 && param2.length() == 0) {
                    fillRequest = new FillRequest(firstParameter);
                }
                else
                {
                    fillRequest = new FillRequest(firstParameter,secondParameter);
                }

                    FillService service = new FillService();
                    FillResponse result = service.fill(fillRequest);

                    String JSON_ResponseBody = gson.toJson(result);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    System.out.println(JSON_ResponseBody);
                    writeString(JSON_ResponseBody, resBody);


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
