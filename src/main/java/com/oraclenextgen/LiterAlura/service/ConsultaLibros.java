package com.oraclenextgen.LiterAlura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaLibros {

    public String consumirAPI (String url) throws IOException, InterruptedException {

        System.out.println ("\nConsultando a la API, un momento");

        String direccionURL = "https://gutendex.com/books?search=" + url;

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccionURL))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        return json;
    }

}
