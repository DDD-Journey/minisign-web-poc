package org.dddjourney.minisignpocbackend.domain;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinisignService {

    private final MinisignRunner minisignRunner;

    public String version() {
        return minisignRunner.version().getProcessFeedback();
    }

    @SneakyThrows
    public ProcessResult verifyFile(byte[] signedFileContent, byte[] signatureFileContent, byte[] publicKeyFileContent) {
        String tempDirectory = createTempDirectory();

        File signedFile = writeTempFile(signedFileContent, Paths.get(tempDirectory, "/signed-file"));
        File signatureFile = writeTempFile(signatureFileContent, Paths.get(tempDirectory, "/signature-file"));
        File publicKeyFile = writeTempFile(publicKeyFileContent, Paths.get(tempDirectory, "/public-key-file"));

        return minisignRunner.verifyFile(
                signedFile.getCanonicalPath(),
                signatureFile.getCanonicalPath(),
                publicKeyFile.getCanonicalPath()
        );
    }

    private File writeTempFile(byte[] signedFileContent, Path path) throws IOException {
        File signedFile = path.toFile();
        FileUtils.writeByteArrayToFile(signedFile, signedFileContent);
        return signedFile;
    }

    @SneakyThrows
    private String createTempDirectory() {
        Path path = Paths.get(FileUtils.getTempDirectory().getAbsolutePath(), UUID.randomUUID().toString());
        return Files.createDirectories(path).toFile().getAbsolutePath();
    }
}
