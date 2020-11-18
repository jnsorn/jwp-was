package controller;

import static http.HttpStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import http.HttpRequest;
import http.HttpResponse;
import http.SimpleHttpRequest;
import servlet.HttpServlet;

class UserControllerTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void doPost() throws IOException {
        HttpRequest request = getHttpRequest("Http_POST.txt");
        HttpResponse response = new HttpResponse();

        HttpServlet userController = new UserController();
        userController.service(request, response);

        assertAll(
            () -> assertThat(response.getStatus()).isEqualTo(FOUND),
            () -> assertThat(response.getHeader("Location")).isNotNull()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Http_GET.txt", "Http_PUT.txt", "Http_DELETE.txt"})
    void doNotAllowedMethod_ThrownException(String path) throws IOException {
        HttpRequest request = getHttpRequest(path);
        HttpResponse response = new HttpResponse();

        HttpServlet userController = new UserController();
        userController.service(request, response);

        assertAll(
            () -> assertThat(response.getStatus()).isEqualTo(METHOD_NOT_ALLOWED),
            () -> assertThat(response.getHeader("Content-Type")).isNotNull()
        );
    }

    private HttpRequest getHttpRequest(String path) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + path));
        InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return SimpleHttpRequest.of(bufferedReader);
    }
}