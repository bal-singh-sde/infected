package com.infected.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TextParser {

    private static ObjectMapper objectMapper = getDefaultObjectMapper(); //only need one object mapper

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }

    public static JsonNode parse(File src) throws IOException {
        return objectMapper.readTree(src);
    }
}
