package org.dddjourney.minisignpocbackend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public record StreamGobbler(
        InputStream inputStream,
        Consumer<String> consumer)
        implements Runnable {

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
    }
}
