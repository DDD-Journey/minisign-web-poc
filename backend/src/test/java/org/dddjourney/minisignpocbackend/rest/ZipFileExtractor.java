package org.dddjourney.minisignpocbackend.rest;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestComponent;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@TestComponent
public class ZipFileExtractor {

    public List<FileMetaData> extractMetaData(byte[] contentAsByteArray) {
        List<FileMetaData> fileMetaData = new ArrayList<>();

        try (final var zin = new ZipInputStream(new ByteArrayInputStream(contentAsByteArray))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                fileMetaData.add(FileMetaData.builder().fileName(entry.getName()).fileSize(entry.getSize()).build());
                zin.closeEntry();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }

        return fileMetaData;
    }

    @Data
    @Builder
    public static class FileMetaData {
        private String fileName;
        private Long fileSize;
    }
}
