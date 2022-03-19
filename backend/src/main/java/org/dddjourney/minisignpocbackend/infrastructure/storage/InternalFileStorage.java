package org.dddjourney.minisignpocbackend.infrastructure.storage;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.dddjourney.minisignpocbackend.business.domain.FileStorage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class InternalFileStorage implements FileStorage {

    private FileStorageProperties properties;

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

}
