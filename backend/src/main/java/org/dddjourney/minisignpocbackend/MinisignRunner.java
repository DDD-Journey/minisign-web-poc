package org.dddjourney.minisignpocbackend;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

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

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        logProcessErrors(process);

        boolean exitedGraceful = waitForCompletion(process);

        ProcessResult processResult = ProcessResult.builder()
                .output(processFeedbackBuffer.toString())
                .exitedGraceful(exitedGraceful)
                .exitValue(process.exitValue())
                .build();

        log.debug("Process result: {}", processResult);

        return processResult;
    }

    @SneakyThrows
    public ProcessResult verifyFile(String payloadFile, String publicKeyFile) {

        String[] command = new String[]{
                "minisign",
                "-Vm",
                payloadFile,
                "-p",
                publicKeyFile
        };

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        logProcessErrors(process);

        boolean exitedGraceful = waitForCompletion(process);

        ProcessResult processResult = ProcessResult.builder()
                .exitedGraceful(exitedGraceful)
                .output(processFeedbackBuffer.toString())
                .build();

        log.debug("Process result: {}", processResult);

        return processResult;
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

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        logProcessErrors(process);
        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);

        writeTextToTerminal(password, process.getOutputStream());

        boolean exitedGraceful = waitForCompletion(process);

        ProcessResult processResult = ProcessResult.builder()
                .exitValue(process.exitValue())
                .exitedGraceful(exitedGraceful)
                .output(processFeedbackBuffer.toString())
                .build();

        log.debug("Process result: {}", processResult);
        return processResult;
    }

    private void logProcessFeedback(Process process) {
        ProcessInputStreamGobbler inputStreamGobbler = new ProcessInputStreamGobbler(process, log::debug);
        Executors.newSingleThreadExecutor().submit(inputStreamGobbler);
    }

    private void logProcessErrors(Process process) {
        ProcessErrorStreamGobbler errorStreamGobbler = new ProcessErrorStreamGobbler(process, log::error);
        Executors.newSingleThreadExecutor().submit(errorStreamGobbler);
    }

    private StringBuffer bufferProcessFeedback(Process process) {
        StringBuffer processInputBuffer = new StringBuffer();
        Executors.newSingleThreadExecutor().submit(new ProcessInputStreamGobbler(process, processInputBuffer::append));
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
