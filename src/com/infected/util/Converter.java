package com.infected.util;

import com.fasterxml.jackson.databind.node.TextNode;

public class Converter {
    public static TextNode stringToTextNode(String s) {
        TextNode t = new TextNode(s);
        return t;
    }

    public static String textNodeToString(TextNode t) {
        String s = String.valueOf(t).replaceAll("\"","");
        return s;
    }
}
