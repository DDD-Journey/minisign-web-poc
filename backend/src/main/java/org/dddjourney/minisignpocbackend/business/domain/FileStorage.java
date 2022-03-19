package org.dddjourney.minisignpocbackend.business.domain;

import java.io.File;
import java.nio.file.Path;

public interface FileStorage {

    File writeTempFile(byte[] signedFileContent, Path path);

    Path createTempDirectory(String sessionId);

}
