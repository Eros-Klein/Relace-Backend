package com.example.demo2;

import lombok.extern.java.Log;

public final class Utils {
    private Utils() {
    }
    public static String getRandomString(int length) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        Logger.getInstance().logInfo("Random string successfully generated", "Utils", "getRandomString");
        return sb.toString();
    }
}
