package org.dddjourney.minisignpocbackend.business.domain.minisign;

import java.io.File;
import java.nio.file.Path;

public interface FileStorage {

    File writeTempFileTo(byte[] signedFileContent, Path path);

    Path createTempDirectoryFor(String sessionId);

    void moveToPermanentFolderFor(String sessionId, File... filesToMove);

    File[] findFilesInDownloadFolder(String sessionId);
}
