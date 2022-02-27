package org.dddjourney.minisignpocbackend;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class MinisignRunner {

    @SneakyThrows
    public String version() {
        String[] args = new String[]{"minisign", "-v"};
        Process process = new ProcessBuilder(args).start();
        return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public String verify() {
        String[] args = new String[]{"minisign", "-Vm", "src/test/resources/minisign/test_payload_file.txt", "-p", "src/test/resources/minisign/minisign_public_key.pub"};

        File outputLogFile = File.createTempFile("output", "log");
        File inputLogFile = File.createTempFile("input", "log");
        File errorLogFile = File.createTempFile("error", "log");

        ProcessBuilder processBuilder = new ProcessBuilder(args)
                .redirectOutput(outputLogFile)
                .redirectInput(inputLogFile)
                .redirectError(errorLogFile);
        Process process = processBuilder.start();
        process.waitFor(5, TimeUnit.SECONDS);

        return FileUtils.readFileToString(outputLogFile, StandardCharsets.UTF_8);
    }
}
