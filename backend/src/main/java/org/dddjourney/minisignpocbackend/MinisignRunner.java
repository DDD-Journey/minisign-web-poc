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

        boolean exitedGraceful = waitForCompletion(process);

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
    public ProcessResult verifyFile(String payloadFile, String publicKeyFile) {

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

        boolean exitedGraceful = waitForCompletion(process);

        String output = FileUtils.readFileToString(outputLogFile, StandardCharsets.UTF_8);

        return ProcessResult.builder()
                .output(output)
                .exitedGraceful(exitedGraceful)
                .build();
    }

    @SneakyThrows
    public ProcessResult signFile(String password, String payloadFile, String secretKeyFile, String signatureFile) {

        String[] command = new String[]{
                "minisign",
                "-Sm",
                payloadFile,
                "-s",
                secretKeyFile,
                "-x",
                signatureFile
        };

        Process process = new ProcessBuilder(command).start();

        logProcessFeedback(process);
        logProcessErrors(process);
        StringBuffer processInputBuffer = bufferProcessFeedabck(process);

        writeTextToTerminal(password, process.getOutputStream());

        boolean exitedGraceful = waitForCompletion(process);

        ProcessResult processResult = ProcessResult.builder()
                .exitValue(process.exitValue())
                .exitedGraceful(exitedGraceful)
                .output(processInputBuffer.toString())
                .build();

        log.debug("Process result: {}", processResult);
        return processResult;
    }

    private void logProcessFeedback(Process process) {
        StreamGobbler inputStreamGobbler = new StreamGobbler(process.getInputStream(), log::debug);
        Executors.newSingleThreadExecutor().submit(inputStreamGobbler);
    }

    private void logProcessErrors(Process process) {
        StreamGobbler errorStreamGobbler = new StreamGobbler(process.getErrorStream(), log::error);
        Executors.newSingleThreadExecutor().submit(errorStreamGobbler);
    }

    private StringBuffer bufferProcessFeedabck(Process process) {
        StringBuffer processInputBuffer = new StringBuffer();
        Executors.newSingleThreadExecutor().submit(new StreamGobbler(process.getInputStream(), processInputBuffer::append));
        return processInputBuffer;
    }

    private void writeTextToTerminal(String text, OutputStream outputStream) {
        PrintWriter outputWriter = new PrintWriter(outputStream);
        outputWriter.println(text);
        outputWriter.flush();
        log.debug("Password written to terminal!");
    }

    private boolean waitForCompletion(Process process) throws InterruptedException {
        return process.waitFor(5, TimeUnit.SECONDS);
    }
}
