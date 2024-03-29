package org.dddjourney.minisignpocbackend.business.domain;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.dddjourney.minisignpocbackend.business.domain.minisign.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinisignService {

    private final Minisign minisign;
    private final FileStorage fileStorage;
    private final SessionCreator sessionCreator;

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

        fileStorage.delete(signedFile, signatureFile, publicKeyFile);
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

        fileStorage.moveToPermanentFolderFor(sessionId, signatureFile);
        fileStorage.delete(unsignedFile, secretKeyFile);

        MinisignProcessResult serviceResult = mapToResult(sessionId, minisignResult);
        log.debug("End - Sign file with process result '{}'", serviceResult);
        return serviceResult;
    }

    public MinisignProcessResult createKeys(String password, String fileName) {
        String sessionId = sessionCreator.createSessionId();
        log.debug("Start - Create keys with fileName '{}' and sessionId '{}'", fileName, sessionId);

        Path tempDirectory = fileStorage.createTempDirectoryFor(sessionId);

        File pubKeyFile = buildPath(tempDirectory, fileName + ".pub").toFile();
        File secretKeyFile = buildPath(tempDirectory, fileName + ".key").toFile();
        MinisignResult minisignResult = minisign.createKeys(pubKeyFile, password, secretKeyFile);

        fileStorage.moveToPermanentFolderFor(
                sessionId,
                minisignResult.getCreatedFiles().toArray(new File[0])
        );

        MinisignProcessResult serviceResult = mapToResult(sessionId, minisignResult);
        log.debug("End - Create keys with process result '{}'", serviceResult);
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

    public MinisignDownloadResult downloadCreatedFiles(String sessionId) throws IOException {

        log.debug("Start - Download created files with sessionId '{}'", sessionId);
        File[] files = collectProducedFilesBy(sessionId);

        if (ArrayUtils.isEmpty(files)) {
            log.error("No files found for sessionId '{}'", sessionId);
            return MinisignDownloadResult.builder()
                    .sessionId(sessionId)
                    .build();
        }

        if (ArrayUtils.getLength(files) > 1) {
            ByteArrayOutputStream compressedFilesStream = fileStorage.createZipFileWith(files);
            ByteArrayResource resource = new ByteArrayResource(compressedFilesStream.toByteArray());
            String fileName = String.format("minisign_%s.zip", sessionId);
            cleanUpDownloads(sessionId, files);
            log.debug("End - Download zip file for sessionId '{}'", sessionId);
            return MinisignDownloadResult.builder()
                    .sessionId(sessionId)
                    .resource(resource)
                    .fileName(fileName)
                    .build();

        } else {
            File file = files[0];
            ByteArrayResource byteArrayResource = new ByteArrayResource(FileUtils.readFileToByteArray(file));
            cleanUpDownloads(sessionId, files);
            log.debug("End - Download single file '{}' for sessionId '{}'", file.getName(), sessionId);
            return MinisignDownloadResult.builder()
                    .sessionId(sessionId)
                    .resource(byteArrayResource)
                    .fileName(file.getName())
                    .build();

        }

    }

    private void cleanUpDownloads(String sessionId, File[] files) {
        fileStorage.delete(files);
        fileStorage.deleteDownloadFolder(sessionId);
    }

    private File[] collectProducedFilesBy(String sessionId) {
        log.debug("Start - Finding produced files by sessionId '{}'", sessionId);
        return fileStorage.findFilesInDownloadFolder(sessionId);
    }
}
