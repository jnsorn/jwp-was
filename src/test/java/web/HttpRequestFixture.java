package web;

import static utils.HttpRequestParser.*;
import static web.HttpResponse.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpRequestFixture {
    public static final String JAVAJIGI_DATA = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    public static final String HEADER = "POST /user/create HTTP/1.1" + NEW_LINE
            + "Host: localhost:8080" + NEW_LINE
            + "Connection: keep-alive" + NEW_LINE
            + "Content-Length: " + JAVAJIGI_DATA.length() + NEW_LINE
            + "Accept: */*" + NEW_LINE
            + EMPTY;
    static final String GET = "GET";
    static final String POST = "POST";
    static final String ROOT = "/";
    static final String INDEX_HTML = "/index.html";
    static final String BODY = NEW_LINE
            + JAVAJIGI_DATA
            + NEW_LINE
            + EMPTY;

    public static final String REQUEST = HEADER + BODY;

    public static HttpRequest createRequest(String method, String uri) throws IOException {
        String request = method + " " + uri + " HTTP/1.1" + NEW_LINE
                + "Host: localhost:8080" + NEW_LINE
                + "Connection: keep-alive" + NEW_LINE
                + "Content-Length: " + JAVAJIGI_DATA.length() + NEW_LINE
                + "Accept: */*" + NEW_LINE
                + EMPTY;
        return new HttpRequest(createBufferedReader(request));
    }

    public static BufferedReader createBufferedReader(String input) {
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes())));
    }
}
