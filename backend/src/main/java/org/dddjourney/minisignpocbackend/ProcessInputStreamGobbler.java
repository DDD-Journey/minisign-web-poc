package org.dddjourney.minisignpocbackend;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

@Slf4j
public record ProcessInputStreamGobbler(
        Process process,
        Consumer<String> consumer)
        implements Runnable {

    @Override
    public void run() {
        try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            processOutputReader.lines().forEach(consumer);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            log.error("Could not read input stream of process", e);
        }
    }
}
