package org.dddjourney.minisignpocbackend.infrastructure.process;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dddjourney.minisignpocbackend.business.domain.minisign.Minisign;
import org.dddjourney.minisignpocbackend.business.domain.minisign.MinisignResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class InternalMinisignProcess implements Minisign {

    @SneakyThrows
    public InternalMinisignResult version() {
        String[] command = {"minisign", "-v"};

        log.debug("Process command {}", Arrays.toString(command));

        Process process = new ProcessBuilder(command).start();

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        StringBuffer processErrorBuffer = bufferProcessError(process);

        boolean exitedGraceful = waitForCompletion(process);

        InternalMinisignResult internalMinisignResult = InternalMinisignResult.builder()
                .processFeedback(processFeedbackBuffer.toString())
                .processError(processErrorBuffer.toString())
                .exitedGraceful(exitedGraceful)
                .exitValue(process.exitValue())
                .build();

        log.debug("Process result: {}", internalMinisignResult);
        return internalMinisignResult;
    }

    @SneakyThrows
    public InternalMinisignResult verifyFile(String payloadFile, String signatureFile, String publicKeyFile) {
        String[] command = {"minisign", "-Vm", payloadFile, "-x", signatureFile,"-p", publicKeyFile};

        log.debug("Process command {}", Arrays.toString(command));

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        StringBuffer processErrorBuffer = bufferProcessError(process);

        boolean exitedGraceful = waitForCompletion(process);

        InternalMinisignResult internalMinisignResult = InternalMinisignResult.builder()
                .exitedGraceful(exitedGraceful)
                .exitValue(process.exitValue())
                .processFeedback(processFeedbackBuffer.toString())
                .processError(processErrorBuffer.toString())
                .build();

        log.debug("Process result: {}", internalMinisignResult);
        return internalMinisignResult;
    }

    @SneakyThrows
    public InternalMinisignResult signFile(String password, String payloadFile, String secretKeyFile, File signatureFile) {
        String[] command = {"minisign", "-Sm", payloadFile, "-s", secretKeyFile, "-x", signatureFile.getAbsolutePath()};

        log.debug("Process command {}", Arrays.toString(command));

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        StringBuffer processErrorBuffer = bufferProcessError(process);

        writeTextToTerminal(password, process.getOutputStream());

        boolean exitedGraceful = waitForCompletion(process);

        InternalMinisignResult internalMinisignResult = InternalMinisignResult.builder()
                .exitValue(process.exitValue())
                .exitedGraceful(exitedGraceful)
                .processFeedback(processFeedbackBuffer.toString())
                .processError(processErrorBuffer.toString())
                .createdFile(signatureFile)
                .build();

        log.debug("Process result: {}", internalMinisignResult);
        return internalMinisignResult;
    }

    @Override
    @SneakyThrows
    public InternalMinisignResult createKeys(File pubKeyFile, String password, File secretKeyFile) {
        String[] command = {"minisign", "-G", "-s", secretKeyFile.getAbsolutePath(), "-p", pubKeyFile.getAbsolutePath()};

        log.debug("Process command {}", Arrays.toString(command));

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        StringBuffer processFeedbackBuffer = bufferProcessFeedback(process);
        StringBuffer processErrorBuffer = bufferProcessError(process);

        writeTextToTerminal(password, process.getOutputStream());
        writeTextToTerminal(password, process.getOutputStream());

        boolean exitedGraceful = waitForCompletion(process);

        InternalMinisignResult internalMinisignResult = InternalMinisignResult.builder()
                .exitValue(process.exitValue())
                .exitedGraceful(exitedGraceful)
                .processFeedback(processFeedbackBuffer.toString())
                .processError(processErrorBuffer.toString())
                .createdFile(secretKeyFile)
                .createdFile(pubKeyFile)
                .build();

        log.debug("Process result: {}", internalMinisignResult);
        return internalMinisignResult;
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
