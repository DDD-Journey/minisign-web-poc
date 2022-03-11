package org.dddjourney.minisignpocbackend;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MinisignRunner {

    @SneakyThrows
    public String version() {
        String[] args = new String[]{"minisign", "-v"};
        Process process = new ProcessBuilder(args).start();
        return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public String verify() {

        File outputLogFile = File.createTempFile("output", "log");
        File inputLogFile = File.createTempFile("input", "log");
        File errorLogFile = File.createTempFile("error", "log");

        String[] args = new String[]{
                "minisign",
                "-Vm",
                "src/test/resources/minisign/test_payload_file.txt",
                "-p",
                "src/test/resources/minisign/minisign_public_key.pub"
        };

        ProcessBuilder processBuilder = new ProcessBuilder(args)
                .redirectOutput(outputLogFile)
                .redirectInput(inputLogFile)
                .redirectError(errorLogFile);
        Process process = processBuilder.start();
        process.waitFor(5, TimeUnit.SECONDS);

        return FileUtils.readFileToString(outputLogFile, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public String sign(String password) {

        String[] args = new String[]{
                "minisign",
                "-Sm",
                "src/test/resources/minisign/test_payload_file.txt",
                "-s",
                "src/test/resources/minisign/minisign_secret_key.key"
        };

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(args);

        Process process = processBuilder.start();

        StreamGobbler inputStreamGobbler = new StreamGobbler(process.getInputStream(), log::debug);
        Executors.newSingleThreadExecutor().submit(inputStreamGobbler);

        StreamGobbler errorStreamGobbler = new StreamGobbler(process.getErrorStream(), log::error);
        Executors.newSingleThreadExecutor().submit(errorStreamGobbler);

        writeTextToTerminal(password, process.getOutputStream());

        log.debug("Password written to terminal!");

        boolean exited = process.waitFor(5, TimeUnit.SECONDS);

        log.debug("Process exited by itself: {}", exited);
        log.debug("Process exited with code: {}", process.exitValue());

        return "";
    }

    private void writeTextToTerminal(String text, OutputStream outputStream) {
        PrintWriter outputWriter = new PrintWriter(outputStream);
        outputWriter.println(text);
        outputWriter.flush();
    }
}
