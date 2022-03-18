package org.dddjourney.minisignpocbackend.domain;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinisignService {

    private final MinisignRunner minisignRunner;

    public String version() {
        return minisignRunner.version().getProcessFeedback();
    }

    @SneakyThrows
    public ProcessResult verifyFile(byte[] signedFileContent, byte[] signatureFileContent, byte[] publicKeyFileContent) {
        String sessionId = createSessionId();
        log.debug("Start - Verify file with sessionId '{}'", sessionId);

        String tempDirectory = createTempDirectory(sessionId);

        File signedFile = writeTempFile(signedFileContent, Paths.get(tempDirectory, "/signed-file"));
        File signatureFile = writeTempFile(signatureFileContent, Paths.get(tempDirectory, "/signature-file"));
        File publicKeyFile = writeTempFile(publicKeyFileContent, Paths.get(tempDirectory, "/public-key-file"));

        ProcessResult processResult = minisignRunner.verifyFile(
                signedFile.getCanonicalPath(),
                signatureFile.getCanonicalPath(),
                publicKeyFile.getCanonicalPath()
        );

        log.debug("End - Verify file with sessionId '{}' and with process result '{}'", sessionId, processResult);
        return processResult;
    }

    @SneakyThrows
    public ProcessResult signFile(String password, byte[] unsignedFileContent, byte[] secretKeyFileContent, String signatureFileName) {
        String sessionId = createSessionId();
        log.debug("Start - Sign file with sessionId '{}'", sessionId);

        String tempDirectory = createTempDirectory(sessionId);

        File unsignedFile = writeTempFile(unsignedFileContent, Paths.get(tempDirectory, "/unsigned-file"));
        File secretKeyFile = writeTempFile(secretKeyFileContent, Paths.get(tempDirectory, "/secret-key-file"));
        Path signatureFilePath = Paths.get(tempDirectory, signatureFileName);

        ProcessResult processResult = minisignRunner.signFile(
                password,
                unsignedFile.getCanonicalPath(),
                secretKeyFile.getCanonicalPath(),
                signatureFilePath.toString()
        );

        log.debug("End - Sign file with sessionId '{}' and with process result '{}'", sessionId, processResult);
        return processResult;
    }

    private File writeTempFile(byte[] signedFileContent, Path path) throws IOException {
        File signedFile = path.toFile();
        FileUtils.writeByteArrayToFile(signedFile, signedFileContent);
        return signedFile;
    }

    @SneakyThrows
    private String createTempDirectory(String sessionId) {
        Path path = Paths.get(FileUtils.getTempDirectory().getAbsolutePath(), sessionId);
        return Files.createDirectories(path).toFile().getAbsolutePath();
    }

    private String createSessionId() {
        return UUID.randomUUID().toString();
    }
}
