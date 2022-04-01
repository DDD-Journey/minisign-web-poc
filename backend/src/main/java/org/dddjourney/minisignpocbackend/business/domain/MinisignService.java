package org.dddjourney.minisignpocbackend.business.domain;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dddjourney.minisignpocbackend.business.rest.ZipFileCreator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinisignService {

    private final Minisign minisign;
    private final FileStorage fileStorage;
    private final SessionCreator sessionCreator;
    private final ZipFileCreator zipFileCreator;

    public String version() {
        return minisign.version().getProcessFeedback();
    }

    @SneakyThrows
    public MinisignProcessResult verifyFile(byte[] signedFileContent, byte[] signatureFileContent, byte[] publicKeyFileContent) {
        String sessionId = sessionCreator.createSessionId();
        log.debug("Start - Verify file with sessionId '{}'", sessionId);

        Path tempDirectory = fileStorage.createTempDirectoryFor(sessionId);

        File signedFile = fileStorage.writeTempFileTo(signedFileContent, buildPath(tempDirectory, "/signed-file"));
        File signatureFile = fileStorage.writeTempFileTo(signatureFileContent, buildPath(tempDirectory, "/signature-file"));
        File publicKeyFile = fileStorage.writeTempFileTo(publicKeyFileContent, buildPath(tempDirectory, "/public-key-file"));

        MinisignResult minisignResult = minisign.verifyFile(
                signedFile.getCanonicalPath(),
                signatureFile.getCanonicalPath(),
                publicKeyFile.getCanonicalPath()
        );

        MinisignProcessResult serviceResult = mapToResult(sessionId, minisignResult);
        log.debug("End - Verify file with process result '{}'", serviceResult);
        return serviceResult;
    }

    @SneakyThrows
    public MinisignProcessResult signFile(String password, byte[] unsignedFileContent, byte[] secretKeyFileContent, String signatureFileName) {
        String sessionId = sessionCreator.createSessionId();
        log.debug("Start - Sign file with sessionId '{}'", sessionId);

        Path tempDirectory = fileStorage.createTempDirectoryFor(sessionId);

        File unsignedFile = fileStorage.writeTempFileTo(unsignedFileContent, buildPath(tempDirectory, "/unsigned-file"));
        File secretKeyFile = fileStorage.writeTempFileTo(secretKeyFileContent, buildPath(tempDirectory, "/secret-key-file"));
        File signatureFile = buildPath(tempDirectory, signatureFileName).toFile();

        MinisignResult minisignResult = minisign.signFile(
                password,
                unsignedFile.getCanonicalPath(),
                secretKeyFile.getCanonicalPath(),
                signatureFile
        );

        fileStorage.moveToPermanentFolderFor(signatureFile, sessionId);

        MinisignProcessResult serviceResult = mapToResult(sessionId, minisignResult);
        log.debug("End - Sign file with process result '{}'", serviceResult);
        return serviceResult;
    }

    private Path buildPath(Path tempDirectory, String fileName) {
        return Paths.get(tempDirectory.toAbsolutePath().toString(), fileName);
    }


    private MinisignProcessResult mapToResult(String sessionId, MinisignResult minisignResult) {
        return MinisignProcessResult.builder()
                .sessionId(sessionId)
                .exitValue(minisignResult.getExitValue())
                .exitedGraceful(minisignResult.isExitedGraceful())
                .createdFiles(
                        minisignResult.getCreatedFiles().stream()
                                .map(File::getName)
                                .collect(Collectors.toList()))
                .processFeedback(minisignResult.getProcessFeedback())
                .processError(minisignResult.getProcessError())
                .build();
    }

    public MinisignDownloadResult downloadCreatedFiles(String sessionId) {

        List<File> files = collectProducedFilesBy(sessionId);
        ByteArrayOutputStream compressedFilesStream = zipFileCreator.downloadZipFile(files);
        ByteArrayResource resource = new ByteArrayResource(compressedFilesStream.toByteArray());
        String fileName = String.format("minisign_%s.zip", sessionId);

        return MinisignDownloadResult.builder()
                .sessionId(sessionId)
                .resource(resource)
                .fileName(fileName)
                .build();
    }

    private List<File> collectProducedFilesBy(String sessionId) {
        log.debug("Start - Finding produced files by sessionId '{}'", sessionId);
        return Arrays.asList(fileStorage.findFilesInDownloadFolder(sessionId));
    }
}
