package org.dddjourney.minisignpocbackend.domain;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MinisignRunner {

    @SneakyThrows
    public ProcessResult version() {
        String[] command = {"minisign", "-v"};

        log.debug("Process command {}", Arrays.toString(command));

        Process process = new ProcessBuilder(command).start();

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        StringBuffer processErrorBuffer = bufferProcessError(process);

        boolean exitedGraceful = waitForCompletion(process);

        ProcessResult processResult = ProcessResult.builder()
                .processFeedback(processFeedbackBuffer.toString())
                .processError(processErrorBuffer.toString())
                .exitedGraceful(exitedGraceful)
                .exitValue(process.exitValue())
                .build();

        log.debug("Process result: {}", processResult);
        return processResult;
    }

    @SneakyThrows
    public ProcessResult verifyFile(String payloadFile, String signatureFile, String publicKeyFile) {
        String[] command = {"minisign", "-Vm", payloadFile, "-x", signatureFile,"-p", publicKeyFile};

        log.debug("Process command {}", Arrays.toString(command));

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        StringBuffer processErrorBuffer = bufferProcessError(process);

        boolean exitedGraceful = waitForCompletion(process);

        ProcessResult processResult = ProcessResult.builder()
                .exitedGraceful(exitedGraceful)
                .exitValue(process.exitValue())
                .processFeedback(processFeedbackBuffer.toString())
                .processError(processErrorBuffer.toString())
                .build();

        log.debug("Process result: {}", processResult);
        return processResult;
    }

    @SneakyThrows
    public ProcessResult signFile(String password, String payloadFile, String secretKeyFile, String signatureFile) {
        String[] command = {"minisign", "-Sm", payloadFile, "-s", secretKeyFile, "-x", signatureFile};

        log.debug("Process command {}", Arrays.toString(command));

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        StringBuffer processErrorBuffer = bufferProcessError(process);

        writeTextToTerminal(password, process.getOutputStream());

        boolean exitedGraceful = waitForCompletion(process);

        ProcessResult processResult = ProcessResult.builder()
                .exitValue(process.exitValue())
                .exitedGraceful(exitedGraceful)
                .processFeedback(processFeedbackBuffer.toString())
                .processError(processErrorBuffer.toString())
                .build();

        log.debug("Process result: {}", processResult);
        return processResult;
    }

    private StringBuffer bufferProcessError(Process process) {
        StringBuffer processErrorBuffer = new StringBuffer();
        ProcessErrorStreamGobbler errorStreamGobbler = new ProcessErrorStreamGobbler(process, processErrorBuffer::append);
        Executors.newSingleThreadExecutor().submit(errorStreamGobbler);
        return processErrorBuffer;
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
        log.debug("Password written to process!");
    }

    private boolean waitForCompletion(Process process) throws InterruptedException {
        return process.waitFor(5, TimeUnit.SECONDS);
    }
}
