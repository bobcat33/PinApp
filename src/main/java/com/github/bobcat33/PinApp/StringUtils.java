package com.github.bobcat33.PinApp;

public class StringUtils {

    public static String formatTitle(String text) {
        return text.replaceAll("\u2013", "-")
                .replaceAll("(\u200B | \u200F | \u200E)", "");
    }

}
