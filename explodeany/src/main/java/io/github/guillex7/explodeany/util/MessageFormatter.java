package io.github.guillex7.explodeany.util;

public class MessageFormatter {
    private MessageFormatter() {
    }

    public static String colorize(String message) {
        return message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
    }
}
