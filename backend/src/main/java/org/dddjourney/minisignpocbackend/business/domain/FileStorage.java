package org.dddjourney.minisignpocbackend.business.domain;

import java.io.File;
import java.nio.file.Path;

public interface FileStorage {

    File writeTempFileTo(byte[] signedFileContent, Path path);

    Path createTempDirectoryFor(String sessionId);

    void moveToPermanentFolderFor(File signatureFilePath, String sessionId);

    File[] findFilesInDownloadFolder(String sessionId);
}
