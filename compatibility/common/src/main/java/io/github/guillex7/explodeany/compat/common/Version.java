package io.github.guillex7.explodeany.compat.common;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Comparable<Version> {
    private static final int INVALID_MAJOR_VERSION = -1;
    private static final Pattern VERSION_PATTERN = Pattern
            .compile("\\d+(?:\\.\\d+)+");

    public int[] numbers;

    public Version(final int[] numbers) {
        this.numbers = numbers;
    }

    public Version(final int major, final int minor, final int patch) {
        this.numbers = new int[] { major, minor, patch };
    }

    public Version(final int major, final int minor) {
        this.numbers = new int[] { major, minor };
    }

    public Version(final int major) {
        this.numbers = new int[] { major };
    }

    public static Version invalid() {
        return new Version(Version.INVALID_MAJOR_VERSION);
    }

    public static Version fromString(final String stringifiedVersion) {
        final Matcher versionMatcher = Version.VERSION_PATTERN.matcher(stringifiedVersion);
        if (versionMatcher.find()) {
            final String[] stringifiedNumbers = versionMatcher.group().split("\\.");
            final int[] numbers = new int[stringifiedNumbers.length];
            for (int i = 0; i < stringifiedNumbers.length; i++) {
                numbers[i] = Integer.parseInt(stringifiedNumbers[i]);
            }
            return new Version(numbers);
        } else {
            return Version.invalid();
        }
    }

    public boolean isValid() {
        return this.numbers[0] != Version.INVALID_MAJOR_VERSION;
    }

    public boolean isBefore(final Version version) {
        return this.compareTo(version) < 0;
    }

    public boolean isEqualOrBefore(final Version version) {
        return this.compareTo(version) < 1;
    }

    public boolean isEqualTo(final Version version) {
        return this.compareTo(version) == 0;
    }

    public boolean isEqualOrAfter(final Version version) {
        return this.compareTo(version) > -1;
    }

    public boolean isAfter(final Version version) {
        return this.compareTo(version) > 0;
    }

    @Override
    public int compareTo(final Version version) {
        final int length = Math.max(this.numbers.length, version.numbers.length);
        for (int i = 0; i < length; i++) {
            final int thisNumber = i < this.numbers.length ? this.numbers[i] : 0;
            final int thatNumber = i < version.numbers.length ? version.numbers[i] : 0;

            if (thisNumber < thatNumber) {
                return -1;
            } else if (thisNumber > thatNumber) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.stream(this.numbers).mapToObj(String::valueOf)
                .reduce((a, b) -> a + "." + b).orElse("(invalid version)");
    }
}
