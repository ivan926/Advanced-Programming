package FileHanderTests;

import FileHandler.FillHandler;
import FileHandler.PersonHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

public class PersonHandlerTest {
    HttpExchange httpExchange;
    PersonHandler personHandler;
    @BeforeEach
    void setUp()
    {   personHandler = new PersonHandler();
        httpExchange = new HttpExchange() {
            @Override
            public Headers getRequestHeaders() {
                Headers header = new Headers();
                header.set("Authorization","e5e2c4e2-ce2d-4478-ba00-42f544c75906");

                return header;
            }

            @Override
            public Headers getResponseHeaders() {
                return null;
            }

            @Override
            public URI getRequestURI() {
                URI uri = null;
                try {
                    //3cdd0911-602a-4dda-a9fc-23191801f40c
                    uri = new URI("/person/9f105073-6209-4285-b0ff-ddef0a867e6b");

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                return uri;
            }

            @Override
            public String getRequestMethod() {
                String method = "GET";
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


                return null;
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
    void sendExchange() throws IOException {

        personHandler.handle(httpExchange);
    }
}
