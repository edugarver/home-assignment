package com.example.products.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public static boolean isBlankOrNull(String str) {
        return str == null || str.isBlank();
    }

}
