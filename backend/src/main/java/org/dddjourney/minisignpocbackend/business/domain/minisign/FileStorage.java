package org.dddjourney.minisignpocbackend.business.domain.minisign;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface FileStorage {

    File writeTempFileTo(byte[] signedFileContent, Path path);

    Path createTempDirectoryFor(String sessionId);

    void moveToPermanentFolderFor(String sessionId, File... filesToMove);

    File[] findFilesInDownloadFolder(String sessionId);

    void deleteDownloadFolder(String sessionId);

    ByteArrayOutputStream createZipFileWith(File... files);

    void delete(File... files);
}
