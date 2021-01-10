package com.health.data.fitday.utils;

public class Utils {
    public static int getTextLength(String paramString) {
        byte b1 = 0;
        for (byte b2 = 0; b2 < paramString.length(); b2++) {
            if (paramString.charAt(b2) > 'y') {
            b1 += 1;
        } else {
            b1++;
        }
    }
    return b1;
}
}
