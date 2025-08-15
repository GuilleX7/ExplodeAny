package io.github.guillex7.explodeany.data;

public class Duration {
    private static final long MILLISECONDS_IN_SECOND = 1000;
    private static final long MILLISECONDS_IN_MINUTE = MILLISECONDS_IN_SECOND * 60;
    private static final long MILLISECONDS_IN_HOUR = MILLISECONDS_IN_MINUTE * 60;
    private static final long MILLISECONDS_IN_DAY = MILLISECONDS_IN_HOUR * 24;
    private static final long MILLISECONDS_IN_WEEK = MILLISECONDS_IN_DAY * 7;
    private static final long MILLISECONDS_IN_TICK = 50;

    private final long milliseconds;

    public static Duration ofMilliseconds(long milliseconds) {
        return new Duration(milliseconds);
    }

    public static Duration ofTicks(long ticks) {
        return new Duration(ticks * MILLISECONDS_IN_TICK);
    }

    public static Duration zero() {
        return new Duration(0);
    }

    public static Duration parse(String duration) {
        long milliseconds = 0;
        if (duration == null || duration.isEmpty()) {
            return new Duration(milliseconds);
        }

        String[] parts = duration.toLowerCase().split(" ");

        for (String part : parts) {
            if (part.endsWith("ms")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 2));
            } else if (part.endsWith("s")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * MILLISECONDS_IN_SECOND;
            } else if (part.endsWith("m")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * MILLISECONDS_IN_MINUTE;
            } else if (part.endsWith("h")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * MILLISECONDS_IN_HOUR;
            } else if (part.endsWith("d")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * MILLISECONDS_IN_DAY;
            } else if (part.endsWith("w")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * MILLISECONDS_IN_WEEK;
            } else {
                // Hint: assume ticks if no suffix is provided, as it is the default unit in
                // Minecraft
                milliseconds += Long.parseLong(part) * MILLISECONDS_IN_TICK;
            }
        }

        return new Duration(milliseconds);
    }

    private Duration(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long asMilliseconds() {
        return milliseconds;
    }

    public long asTicks() {
        return milliseconds / MILLISECONDS_IN_TICK;
    }

    public boolean isZero() {
        return milliseconds == 0;
    }

    @Override
    public String toString() {
        long weeks = (milliseconds / MILLISECONDS_IN_WEEK);
        long days = ((milliseconds % MILLISECONDS_IN_WEEK) / MILLISECONDS_IN_DAY);
        long hours = ((milliseconds % MILLISECONDS_IN_DAY) / MILLISECONDS_IN_HOUR);
        long minutes = ((milliseconds % MILLISECONDS_IN_HOUR) / MILLISECONDS_IN_MINUTE);
        long seconds = ((milliseconds % MILLISECONDS_IN_MINUTE) / MILLISECONDS_IN_SECOND);

        StringBuilder builder = new StringBuilder();
        if (weeks > 0) {
            builder.append(weeks).append("w ");
        }
        if (days > 0) {
            builder.append(days).append("d ");
        }
        if (hours > 0) {
            builder.append(hours).append("h ");
        }
        if (minutes > 0) {
            builder.append(minutes).append("m ");
        }
        if (seconds > 0) {
            builder.append(seconds).append("s ");
        }
        if (milliseconds % MILLISECONDS_IN_SECOND > 0) {
            builder.append(milliseconds % MILLISECONDS_IN_SECOND).append("ms");
        }

        if (builder.length() == 0) {
            return "0s";
        }

        return builder.toString().trim();
    }
}
