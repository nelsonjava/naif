package com.naif.tools.dbf;

public class BeanUtils {

    public static String attribute(String attribute) {
        char first = Character.toLowerCase(attribute.charAt(0));
        return first + attribute.substring(1);
    }

}