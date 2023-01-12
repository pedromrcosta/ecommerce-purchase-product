package com.pedrocosta.ecommerceproject.utils;

public class FindEnumValue {

    public static <E extends Enum<E>> E lookup(Class<E> e, String id) {
        E result = null;
        try {
             result = Enum.valueOf(e, id);
        } catch (IllegalArgumentException ex) {
            // log error or something here
            throw new RuntimeException("Invalid value for enum");
        }

        return result;
    }

}
