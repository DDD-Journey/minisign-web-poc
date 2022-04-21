package org.dddjourney.minisignpocbackend.business.domain.minisign;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public interface ZipFileCreator {

    ByteArrayOutputStream createZipFileWith(File... files);
}
