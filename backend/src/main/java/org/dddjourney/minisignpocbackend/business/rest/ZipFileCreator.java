package org.dddjourney.minisignpocbackend.business.rest;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface ZipFileCreator {

    ByteArrayOutputStream downloadZipFile(List<String> fileNames);
}
