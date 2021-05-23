package com.health.data.fitday.utils;

import java.util.regex.Pattern;

public class Validator {
    public static final String REGEX_CHINESE = "^[{1,9}$";

    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    public static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

    public static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";

    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    public static boolean isChinese(String paramString) {
        return Pattern.matches("^[\u4e00-\u9fa5],{0,}$", paramString);
    }

    public static boolean isEmail(String paramString) {
        return Pattern.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", paramString);
    }

    public static boolean isIDCard(String paramString) {
        return Pattern.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)", paramString);
    }

    public static boolean isIPAddress(String paramString) {
        return Pattern.matches("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})", paramString);
    }

    public static boolean isMobile(String paramString) {
        return Pattern.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", paramString);
    }

    public static boolean isPassword(String paramString) {
        return Pattern.matches("^[a-zA-Z0-9]{6,16}$", paramString);
    }

    public static boolean isUrl(String paramString) {
        return Pattern.matches("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", paramString);
    }

    public static boolean isUserName(String paramString) {
        return Pattern.matches("^[a-zA-Z]\\w{5,17}$", paramString);
    }
}
