package org.dddjourney.minisignpocbackend.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.dddjourney.minisignpocbackend.business.domain.minisign.FileStorage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalFileStorage implements FileStorage {

    private final FileStorageProperties properties;

    @SneakyThrows
    public File writeTempFileTo(byte[] signedFileContent, Path path) {
        File signedFile = path.toFile();
        FileUtils.writeByteArrayToFile(signedFile, signedFileContent);
        return signedFile;
    }

    @SneakyThrows
    public Path createTempDirectoryFor(String sessionId) {
        Path path = Paths.get(FileUtils.getTempDirectory().getAbsolutePath(), sessionId);
        return Files.createDirectories(path);
    }

    @Override
    public void moveToPermanentFolderFor(String sessionId, File... filesToMove) {

        Set<String> filePaths = Arrays.stream(filesToMove)
                .map(File::getAbsolutePath)
                .collect(Collectors.toSet());

        log.debug("Start - Moving file from '{}'", filePaths);

        Path downloadFolderPath = Paths.get(properties.getDownloadFolder(), sessionId);

        if (!downloadFolderPath.toFile().mkdirs()) {
            throw new IllegalStateException("Creating folder " + downloadFolderPath.toAbsolutePath() + " was not successful!");
        }

        List<File> destinationFiles = Arrays.stream(filesToMove)
                .map(file -> moveFile(downloadFolderPath, file))
                .toList();

        Set<String> paths = destinationFiles.stream()
                .map(File::getAbsolutePath)
                .collect(Collectors.toSet());

        log.debug("End - Moving files to '{}'", paths);
    }

    private File moveFile(Path downloadFolderPath, File file) {
        File destinationFile = Paths.get(downloadFolderPath.toString(), file.getName()).toFile();
        if (!file.renameTo(destinationFile)) {
            throw new IllegalStateException("Move file to " + destinationFile.getAbsolutePath() + " was not successful!");
        }
        return destinationFile;
    }

    @Override
    public File[] findFilesInDownloadFolder(String sessionId) {
        File directory = Paths.get(properties.getDownloadFolder(), sessionId).toFile();
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Folder doesn't exists: " + directory.getAbsolutePath());
        }

        File[] files = directory.listFiles();
        log.debug("'{}' files found in folder.", files != null ? files.length : 0);
        return files;
    }

}
