package FileHanderTests;

import FileHandler.LoginHandler;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterHandlerTest {

    LoginHandler loginHand;
    HttpExchange exchange;
    String serverHost;
    String serverPort;

    @BeforeEach
    void setupValues()
    {
        loginHand = new LoginHandler();
        serverHost = "localhost";
        serverPort = "8080";
    }

    @Test
    void registerTestFromServerToDAO() throws IOException {
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            String strUrl = "http://" + serverHost + ":" + serverPort + "/user/register";
            URL url = new URL(strUrl);


            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();


            // Specify that we are sending an HTTP GET request
            http.setRequestMethod("POST");

            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(true);



            //create a request body
            http.setRequestProperty("Content-Type", "application/json");
            // Add an auth token to the request in the HTTP "Authorization" header
            //http.addRequestProperty("Authorization", "afj");

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            OutputStream outputStream = http.getOutputStream();



            String reqData = "{ \"username\": \"navis462\", \"password\": \"holycow\", \"email\":\"navis462@ymail.com\"," +
                    "\"firstName\": \"Defenia\", \"lastName\":\"Baraniston\",\"gender\":\"F\" }";

            outputStream.write(reqData.getBytes());

            // Connect to the server and send the HTTP request
           // http.connect();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
              //  InputStream respBody = http.getInputStream();

                // Extract JSON data from the HTTP response body
                // String respData = readString(respBody);

                // Display the JSON data returned from the server
                // System.out.println(respData);
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());

                // Get the error stream containing the HTTP response body (if any)
                InputStream respBody = http.getErrorStream();

                // Extract data from the HTTP response body
                 String respData = readString(respBody);

                // Display the data returned from the server
                // System.out.println(respData);
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
    }

    /*
       The readString method shows how to read a String from an InputStream.
   */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
