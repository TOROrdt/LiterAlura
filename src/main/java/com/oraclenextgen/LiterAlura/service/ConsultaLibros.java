package com.oraclenextgen.LiterAlura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaLibros {

    public String consumirAPI (String url) {

        System.out.println ("\nConsultando a la API, un momento");

        String direccionURL = "https://gutendex.com/books?search=" + url.replace(" ", "%20");

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccionURL))
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            return json;

        } catch (IOException | InterruptedException e) {

            System.out.print("No se pudo realizar la consulta a la API: " + e.getMessage() + "\n");
            return null;
        }
    }

}
