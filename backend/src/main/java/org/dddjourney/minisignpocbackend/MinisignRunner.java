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
    public ProcessResult version() {

        Process process = new ProcessBuilder("minisign", "-v").start();

        boolean exitedGraceful = wait(process);

        String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);

        ProcessResult processResult = ProcessResult.builder()
                .output(output)
                .exitedGraceful(exitedGraceful)
                .exitValue(process.exitValue())
                .build();

        log.debug("Process result: {}", processResult);

        return processResult;
    }

    @SneakyThrows
    public ProcessResult verify(String payloadFile, String publicKeyFile) {

        File outputLogFile = File.createTempFile("output", "log");
        File inputLogFile = File.createTempFile("input", "log");
        File errorLogFile = File.createTempFile("error", "log");

        String[] command = new String[]{
                "minisign",
                "-Vm",
                payloadFile,
                "-p",
                publicKeyFile
        };

        Process process = new ProcessBuilder(command)
                .redirectOutput(outputLogFile)
                .redirectInput(inputLogFile)
                .redirectError(errorLogFile)
                .start();

        boolean exitedGraceful = wait(process);

        String output = FileUtils.readFileToString(outputLogFile, StandardCharsets.UTF_8);

        return ProcessResult.builder()
                .output(output)
                .exitedGraceful(exitedGraceful)
                .build();
    }

    @SneakyThrows
    public ProcessResult sign(String password, String payloadFile, String secretKeyFile, String signatureFile) {

        String[] args = new String[]{
                "minisign",
                "-Sm",
                payloadFile,
                "-s",
                secretKeyFile,
                "-x",
                signatureFile
        };

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(args);

        Process process = processBuilder.start();

        StreamGobbler inputStreamGobbler = new StreamGobbler(process.getInputStream(), log::debug);
        Executors.newSingleThreadExecutor().submit(inputStreamGobbler);

        StreamGobbler errorStreamGobbler = new StreamGobbler(process.getErrorStream(), log::error);
        Executors.newSingleThreadExecutor().submit(errorStreamGobbler);

        writeTextToTerminal(password, process.getOutputStream());

        boolean exitedGraceful = wait(process);

        log.debug("Process exited by itself: {}", exitedGraceful);
        log.debug("Process exited with code: {}", process.exitValue());

        ProcessResult processResult = ProcessResult.builder()
                .exitValue(process.exitValue())
                .exitedGraceful(exitedGraceful)
                .build();

        log.debug("Process result: {}", processResult);
        return processResult;
    }

    private void writeTextToTerminal(String text, OutputStream outputStream) {
        PrintWriter outputWriter = new PrintWriter(outputStream);
        outputWriter.println(text);
        outputWriter.flush();
        log.debug("Password written to terminal!");
    }

    private boolean wait(Process process) throws InterruptedException {
        return process.waitFor(5, TimeUnit.SECONDS);
    }
}
