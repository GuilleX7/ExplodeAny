package io.github.guillex7.explodeany.data;

public class Duration {
    private static final long MILLISECONDS_IN_SECOND = 1000;
    private static final long MILLISECONDS_IN_MINUTE = Duration.MILLISECONDS_IN_SECOND * 60;
    private static final long MILLISECONDS_IN_HOUR = Duration.MILLISECONDS_IN_MINUTE * 60;
    private static final long MILLISECONDS_IN_DAY = Duration.MILLISECONDS_IN_HOUR * 24;
    private static final long MILLISECONDS_IN_WEEK = Duration.MILLISECONDS_IN_DAY * 7;
    private static final long MILLISECONDS_IN_TICK = 50;

    private final long milliseconds;

    public static Duration ofMilliseconds(final long milliseconds) {
        return new Duration(milliseconds);
    }

    public static Duration ofTicks(final long ticks) {
        return new Duration(ticks * Duration.MILLISECONDS_IN_TICK);
    }

    public static Duration zero() {
        return new Duration(0);
    }

    public static Duration parse(final String duration) {
        long milliseconds = 0;
        if (duration == null || duration.isEmpty()) {
            return new Duration(milliseconds);
        }

        final String[] parts = duration.toLowerCase().split(" ");

        for (final String part : parts) {
            if (part.endsWith("ms")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 2));
            } else if (part.endsWith("s")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * Duration.MILLISECONDS_IN_SECOND;
            } else if (part.endsWith("m")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * Duration.MILLISECONDS_IN_MINUTE;
            } else if (part.endsWith("h")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * Duration.MILLISECONDS_IN_HOUR;
            } else if (part.endsWith("d")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * Duration.MILLISECONDS_IN_DAY;
            } else if (part.endsWith("w")) {
                milliseconds += Long.parseLong(part.substring(0, part.length() - 1)) * Duration.MILLISECONDS_IN_WEEK;
            } else {
                // Hint: assume ticks if no suffix is provided, as it is the default unit in
                // Minecraft
                milliseconds += Long.parseLong(part) * Duration.MILLISECONDS_IN_TICK;
            }
        }

        return new Duration(milliseconds);
    }

    private Duration(final long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long asMilliseconds() {
        return this.milliseconds;
    }

    public long asTicks() {
        return this.milliseconds / Duration.MILLISECONDS_IN_TICK;
    }

    public boolean isZero() {
        return this.milliseconds == 0;
    }

    @Override
    public String toString() {
        final long weeks = (this.milliseconds / Duration.MILLISECONDS_IN_WEEK);
        final long days = ((this.milliseconds % Duration.MILLISECONDS_IN_WEEK) / Duration.MILLISECONDS_IN_DAY);
        final long hours = ((this.milliseconds % Duration.MILLISECONDS_IN_DAY) / Duration.MILLISECONDS_IN_HOUR);
        final long minutes = ((this.milliseconds % Duration.MILLISECONDS_IN_HOUR) / Duration.MILLISECONDS_IN_MINUTE);
        final long seconds = ((this.milliseconds % Duration.MILLISECONDS_IN_MINUTE) / Duration.MILLISECONDS_IN_SECOND);

        final StringBuilder builder = new StringBuilder();
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
        if (this.milliseconds % Duration.MILLISECONDS_IN_SECOND > 0) {
            builder.append(this.milliseconds % Duration.MILLISECONDS_IN_SECOND).append("ms");
        }

        if (builder.length() == 0) {
            return "0s";
        }

        return builder.toString().trim();
    }
}
