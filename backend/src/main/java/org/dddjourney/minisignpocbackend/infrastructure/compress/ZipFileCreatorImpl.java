package org.dddjourney.minisignpocbackend.infrastructure.compress;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.dddjourney.minisignpocbackend.business.rest.ZipFileCreator;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class ZipFileCreatorImpl implements ZipFileCreator {

    @SneakyThrows
    public ByteArrayOutputStream downloadZipFile(List<String> fileNames) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteOutputStream);

        for (String filename : fileNames) {
            File file = new File(filename);
            ZipEntry zipEntry = new ZipEntry(filename);
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, zipOutputStream);
            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();

        return byteOutputStream;

    }

}
