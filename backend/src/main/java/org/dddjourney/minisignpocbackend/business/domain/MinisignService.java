package org.dddjourney.minisignpocbackend.business.domain;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinisignService {

    private final Minisign minisign;
    private final FileStorage fileStorage;

    public String version() {
        return minisign.version().getProcessFeedback();
    }

    @SneakyThrows
    public MinisignServiceResult verifyFile(byte[] signedFileContent, byte[] signatureFileContent, byte[] publicKeyFileContent) {
        String sessionId = createSessionId();
        log.debug("Start - Verify file with sessionId '{}'", sessionId);

        Path tempDirectory = fileStorage.createTempDirectory(sessionId);

        File signedFile = fileStorage.writeTempFile(signedFileContent, buildPath(tempDirectory, "/signed-file"));
        File signatureFile = fileStorage.writeTempFile(signatureFileContent, buildPath(tempDirectory, "/signature-file"));
        File publicKeyFile = fileStorage.writeTempFile(publicKeyFileContent, buildPath(tempDirectory, "/public-key-file"));

        MinisignResult minisignResult = minisign.verifyFile(
                signedFile.getCanonicalPath(),
                signatureFile.getCanonicalPath(),
                publicKeyFile.getCanonicalPath()
        );

        log.debug("End - Verify file with sessionId '{}' and with process result '{}'", sessionId, minisignResult);
        return mapToResult(sessionId, minisignResult);
    }

    @SneakyThrows
    public MinisignServiceResult signFile(String password, byte[] unsignedFileContent, byte[] secretKeyFileContent, String signatureFileName) {
        String sessionId = createSessionId();
        log.debug("Start - Sign file with sessionId '{}'", sessionId);

        Path tempDirectory = fileStorage.createTempDirectory(sessionId);

        File unsignedFile = fileStorage.writeTempFile(unsignedFileContent, buildPath(tempDirectory, "/unsigned-file"));
        File secretKeyFile = fileStorage.writeTempFile(secretKeyFileContent, buildPath(tempDirectory, "/secret-key-file"));
        Path signatureFilePath = buildPath(tempDirectory, signatureFileName);

        MinisignResult minisignResult = minisign.signFile(
                password,
                unsignedFile.getCanonicalPath(),
                secretKeyFile.getCanonicalPath(),
                signatureFilePath.toString()
        );

        log.debug("End - Sign file with sessionId '{}' and with process result '{}'", sessionId, minisignResult);
        return mapToResult(sessionId, minisignResult);
    }

    private Path buildPath(Path tempDirectory, String fileName) {
        return Paths.get(tempDirectory.toAbsolutePath().toString(), fileName);
    }

    private String createSessionId() {
        return UUID.randomUUID().toString();
    }

    private MinisignServiceResult mapToResult(String sessionId, MinisignResult minisignResult) {
        return MinisignServiceResult.builder()
                .sessionId(sessionId)
                .exitValue(minisignResult.getExitValue())
                .exitedGraceful(minisignResult.isExitedGraceful())
                .createdFiles(minisignResult.getCreatedFiles())
                .processFeedback(minisignResult.getProcessFeedback())
                .processError(minisignResult.getProcessError())
                .build();
    }
}