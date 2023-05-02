package FileHandler;

import DAO.AuthtokenDAO;
import DAO.DataAccessError;
import DAO.Database;
import Request_Response.EventRequest;
import Request_Response.EventResponse;
import Request_Response.PersonRequest;
import Request_Response.PersonResponse;
import Service.EventService;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import model.authtoken;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.Connection;

public class EventHandler extends Handler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Gson gson = new Gson();
        System.out.println("Receiving Event Request");
        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                if( reqHeaders.containsKey("Authorization") ) {
                    Database database = Database.getInstance();
                    Connection conn = database.getConnection();

                    AuthtokenDAO authtokenDAO = new AuthtokenDAO(conn);


                    URI uri = exchange.getRequestURI();

                    String authToken = reqHeaders.getFirst("Authorization");
                    authtoken authtoken = authtokenDAO.getAuthToken(authToken);

                    if( !authToken.equals( authtoken.getAuthToken()) )
                    {
                        success = false;

                    }
                    else {


                        String path = uri.getPath();
                        String firstParameter = null;
                        String secondParameter = null;
                        int characterCount = 0;

                        char[] CharpathArray = path.toCharArray();
                        StringBuilder param1 = new StringBuilder();
                        StringBuilder param2 = new StringBuilder();

                        for (char currentLetter : CharpathArray) {
                            if (characterCount == 1) {
                                if (currentLetter != '/') {
                                    param1.append(currentLetter);
                                }
                            }

                            if (characterCount == 2) {
                                param2.append(currentLetter);
                            }

                            if (Character.compare(currentLetter, '/') == 0) {

                                characterCount++;

                            }


                        }

                        firstParameter = param1.toString();

                        secondParameter = param2.toString();


                        System.out.println(secondParameter);


                        //creating gson object
                        EventRequest eventRequest = null;

                        //determine whether an optional generation count was given

                        if(secondParameter.length() == 0)
                        {
                            eventRequest = new EventRequest(false,authtoken);
                        }
                        else {
                            eventRequest = new EventRequest(secondParameter, true,authtoken);
                        }

                        EventService service = new EventService();
                        EventResponse result = service.event(eventRequest);

                        String JSON_ResponseBody = gson.toJson(result);
                        if(result.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        }
                        else
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                        }

                        OutputStream resBody = exchange.getResponseBody();
                        System.out.println(JSON_ResponseBody);
                        writeString(JSON_ResponseBody, resBody);

                        System.out.println(resBody);

                        resBody.close();




                        success = true;
                    }






                }
            }

            if (!success) {
                System.out.println("Auth token does not exist");
                PersonResponse personResponse = new PersonResponse("Error: Invalid auth token");

                String JSON_ResponseBody = gson.toJson(personResponse);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream resBody = exchange.getResponseBody();
                writeString(JSON_ResponseBody, resBody);

                resBody.close();
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
        } catch (DataAccessError dataAccessError) {
            System.out.println("Had issues opening the database");
            dataAccessError.printStackTrace();
        }


    }
}
