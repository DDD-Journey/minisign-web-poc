package org.dddjourney.minisignpocbackend.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.dddjourney.minisignpocbackend.business.domain.FileStorage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalFileStorage implements FileStorage {

    private final FileStorageProperties properties;

    @SneakyThrows
    public File writeTempFile(byte[] signedFileContent, Path path) {
        File signedFile = path.toFile();
        FileUtils.writeByteArrayToFile(signedFile, signedFileContent);
        return signedFile;
    }

    @SneakyThrows
    public Path createTempDirectory(String sessionId) {
        Path path = Paths.get(FileUtils.getTempDirectory().getAbsolutePath(), sessionId);
        return Files.createDirectories(path);
    }

    public File moveToPermanentFolder(File file, String sessionId) {
        log.debug("Start - Moving file from '{}'", file.getAbsolutePath());

        Path downloadFolderPath = Paths.get(properties.getDownloadFolder(), sessionId);

        if (!downloadFolderPath.toFile().mkdirs()) {
            throw new IllegalStateException("Creating folder " + downloadFolderPath.toAbsolutePath() + " was not successful!");
        }

        File destinationFile = Paths.get(downloadFolderPath.toString(), file.getName()).toFile();

        if (!file.renameTo(destinationFile)) {
            throw new IllegalStateException("Move file to " + destinationFile.getAbsolutePath() + " was not successful!");
        }

        log.debug("End - Moving file to '{}'", destinationFile.getAbsolutePath());
        return destinationFile;
    }

}
