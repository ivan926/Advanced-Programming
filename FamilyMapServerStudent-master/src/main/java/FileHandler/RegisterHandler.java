package FileHandler;

import Request_Response.RegisterRequest;
import Request_Response.RegisterResponse;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import passoffresult.LoginResult;
import passoffresult.RegisterResult;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

public class RegisterHandler extends Handler {
    public RegisterHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {



        boolean success = false;

        try {


            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                Headers reqHeaders = exchange.getRequestHeaders();


                        InputStream reqBody = exchange.getRequestBody();

                        String reqData = readString(reqBody);

                        // Display/log the request JSON data
                        //System.out.println(reqData);

                        //creating gson object
                        Gson gson = new Gson();

						RegisterRequest request = (RegisterRequest) gson.fromJson(reqData, RegisterRequest.class);

						RegisterService service = new RegisterService();
						RegisterResponse result = service.register(request);

						String JSON_ResponseBody = gson.toJson(result);

						exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
						OutputStream resBody = exchange.getResponseBody();
						System.out.println(JSON_ResponseBody);
						writeString(JSON_ResponseBody,resBody);


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

    /*
       The readString method shows how to read a String from an InputStream.
   */
//    private String readString(InputStream is) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        InputStreamReader sr = new InputStreamReader(is);
//        char[] buf = new char[1024];
//        int len;
//        while ((len = sr.read(buf)) > 0) {
//            sb.append(buf, 0, len);
//        }
//        return sb.toString();
//    }
//
//    private void writeString(String str, OutputStream os) throws IOException {
//        OutputStreamWriter sw = new OutputStreamWriter(os);
//        sw.write(str);
//        sw.flush();
//    }


}
