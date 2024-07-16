package com.oraclenextgen.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos {

    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T obtenerDatos (String json, Class<T> clase) {

        try {
            return objectMapper.readValue(json, clase);

        } catch (JsonProcessingException e) {
            System.out.println("No fue posible convertir el archivo json a datos del programa");
            return null;
        }
    }
}
