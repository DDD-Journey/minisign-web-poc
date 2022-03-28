package org.dddjourney.minisignpocbackend.business.rest;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dddjourney.minisignpocbackend.business.domain.MinisignService;
import org.dddjourney.minisignpocbackend.business.domain.MinisignServiceResult;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MinisignController {

    private final MinisignService minisignService;
    private final ZipFileCreator zipFileCreator;

    @GetMapping("/version")
    public ResponseEntity<String> getMinisignVersion() {
        return ResponseEntity.ok(minisignService.version());
    }

    @CrossOrigin(origins = "http://localhost:8082")
    @PostMapping(path = "/verify-file", consumes = {"multipart/form-data"})
    public ResponseEntity<ProcessResultResource> verifyFile(
            @RequestParam("signed-file") MultipartFile signedFile,
            @RequestParam("signature-file") MultipartFile signatureFile,
            @RequestParam("public-key-file") MultipartFile publicKeyFile) {


        MinisignServiceResult serviceResult = minisignService.verifyFile(
                convertToBytes(signedFile),
                convertToBytes(signatureFile),
                convertToBytes(publicKeyFile)
        );

        return new ResponseEntity<>(mapToProcessResultResource(serviceResult), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:8082")
    @PostMapping(path = "/sign-file", consumes = {"multipart/form-data"})
    public ResponseEntity<ProcessResultResource> signFile(
            @RequestParam("password") String password,
            @RequestParam("unsigned-file") MultipartFile unsignedFile,
            @RequestParam("secret-key-file") MultipartFile secretKeyFile,
            @RequestParam("signature-file-name") String signatureFileName) {

        MinisignServiceResult serviceResult = minisignService.signFile(
                password,
                convertToBytes(unsignedFile),
                convertToBytes(secretKeyFile),
                signatureFileName
        );

        return new ResponseEntity<>(mapToProcessResultResource(serviceResult), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:8082")
    @SneakyThrows
    @GetMapping(path = "download-files/{session-id}", produces = "application/zip")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("session-id") String sessionId) {

        List<File> files = minisignService.collectProducedFilesBy(sessionId);
        ByteArrayOutputStream compressedFilesStream = zipFileCreator.downloadZipFile(files);
        ByteArrayResource resource = new ByteArrayResource(compressedFilesStream.toByteArray());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, buildContentDispositionHeaderValue(sessionId));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    private String buildContentDispositionHeaderValue(String sessionId) {
        return String.format("attachment; filename=\"minisign_%s.zip\"", sessionId);
    }

    private ProcessResultResource mapToProcessResultResource(MinisignServiceResult serviceResult) {
        return ProcessResultResource.builder()
                .sessionId(serviceResult.getSessionId())
                .processFeedback(serviceResult.getProcessFeedback())
                .exitedGraceful(serviceResult.isExitedGraceful())
                .processError(serviceResult.getProcessError())
                .exitValue(serviceResult.getExitValue())
                .createdFiles(serviceResult.getCreatedFiles())
                .build();
    }

    @SneakyThrows
    private byte[] convertToBytes(MultipartFile signedFile) {
        return signedFile.getBytes();
    }

}
