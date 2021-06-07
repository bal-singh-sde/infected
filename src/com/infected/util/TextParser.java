package com.infected.util;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class TextParser {

    private static ObjectMapper objectMapper = getDefaultObjectMapper(); //only need one object mapper

    public static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper();
    }

    public static JsonNode parse(File src) throws IOException {
        return objectMapper.readTree(src);
    }

    public static JsonNode getNewNode(JsonNode jsonNode, String key, String newValue) {
        ((ObjectNode) jsonNode).put(key, newValue);
        return jsonNode;
    }

    public static void write(File src, JsonNode jsonNode) throws IOException {
        // use DefaultPrettyPrinter to auto-format json after writing
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        // replace current json with new JsonNode
        writer.writeValue(src, jsonNode);
    }
}
