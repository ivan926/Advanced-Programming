package FileHanderTests;

import FileHandler.LoadHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadHandlerTest {
    HttpExchange httpExchange;
    LoadHandler loadHandler;

    @BeforeEach
            void setup() {

        loadHandler = new LoadHandler();
        Path path = Paths.get("passoffFiles/LoadData.json");

        byte[] data = null;

        {
            try {
                data = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        InputStream input = new ByteArrayInputStream(data);

        httpExchange = new HttpExchange() {
            @Override
            public Headers getRequestHeaders() {
                Headers header = new Headers();

                return null;
            }

            @Override
            public Headers getResponseHeaders() {
                return null;
            }

            @Override
            public URI getRequestURI() {
                URI uri = null;
                try {
                    uri = new URI("/load");

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                return uri;
            }

            @Override
            public String getRequestMethod() {
                String method = "POST";
                return method;
            }

            @Override
            public HttpContext getHttpContext() {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getRequestBody() {


                return input;
            }

            @Override
            public OutputStream getResponseBody() {
                return null;
            }

            @Override
            public void sendResponseHeaders(int rCode, long responseLength) throws IOException {

            }

            @Override
            public InetSocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public int getResponseCode() {
                return 0;
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public void setStreams(InputStream i, OutputStream o) {

            }

            @Override
            public HttpPrincipal getPrincipal() {
                return null;
            }
        };



    }

    @Test
    void loadJsonTestGeneric() throws IOException {


        loadHandler.handle(httpExchange);


    }



}
