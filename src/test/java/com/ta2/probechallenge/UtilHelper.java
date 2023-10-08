package com.ta2.probechallenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class UtilHelper {

    public static String getContentFile(String filename) {
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Thread
                        .currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(filename)))
        ).lines().parallel().collect(Collectors.joining());
    }
}
