package org.dddjourney.minisignpocbackend.infrastructure.compress;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.dddjourney.minisignpocbackend.business.domain.minisign.ZipFileCreator;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class ZipFileCreatorImpl implements ZipFileCreator {

    @SneakyThrows
    public ByteArrayOutputStream createZipFileWith(File... files) {
        List<String> fileNames = Arrays.asList(files).stream().map(File::getName).toList();

        log.debug("Start - Create ZIP file for '{}'", fileNames);

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteOutputStream);

        for (File file : files) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, zipOutputStream);
            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();

        log.debug("End - Create ZIP file for '{}'", fileNames);
        return byteOutputStream;
    }

}
