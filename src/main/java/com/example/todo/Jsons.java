package com.example.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Jsons {
    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();
    private Jsons() {

    }



    public static JsonNode convertJSON(String contentJson) throws JsonProcessingException {
        return DEFAULT_MAPPER.readTree(contentJson);
    }
}
