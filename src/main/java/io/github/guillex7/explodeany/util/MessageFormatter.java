package io.github.guillex7.explodeany.util;

import io.github.guillex7.explodeany.ExplodeAny;

public class MessageFormatter {
	public static String sign(String message) {
		return String.format("[%s] %s", ExplodeAny.getInstance().getDescription().getName(), message);
	}
	
	public static String colorize(String message) {
		return message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
	}
}
