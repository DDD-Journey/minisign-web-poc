package org.dddjourney.minisignpocbackend.rest;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dddjourney.minisignpocbackend.domain.MinisignService;
import org.dddjourney.minisignpocbackend.domain.ProcessResult;
import org.dddjourney.minisignpocbackend.domain.ZipFileCreator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
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

    @PostMapping(path = "/verify-file", consumes = {"multipart/form-data"})
    public ResponseEntity<ProcessResultResource> verifyFile(
            @RequestParam("signed-file") MultipartFile signedFile,
            @RequestParam("signature-file") MultipartFile signatureFile,
            @RequestParam("public-key-file") MultipartFile publicKeyFile) {


        ProcessResult processResult = minisignService.verifyFile(
                convertToBytes(signedFile),
                convertToBytes(signatureFile),
                convertToBytes(publicKeyFile)
        );

        return new ResponseEntity<>(mapToProcessResultResource(processResult), HttpStatus.CREATED);
    }

    @PostMapping(path = "/sign-file", consumes = {"multipart/form-data"})
    public ResponseEntity<ProcessResultResource> signFile(
            @RequestParam("password") String password,
            @RequestParam("unsigned-file") MultipartFile unsignedFile,
            @RequestParam("secret-key-file") MultipartFile secretKeyFile,
            @RequestParam("signature-file-name") String signatureFileName) {

        ProcessResult processResult = minisignService.signFile(
                password,
                convertToBytes(unsignedFile),
                convertToBytes(secretKeyFile),
                signatureFileName
        );

        return new ResponseEntity<>(mapToProcessResultResource(processResult), HttpStatus.CREATED);
    }

    @SneakyThrows
    @GetMapping(path = "download-files", produces = "application/zip")
    public ResponseEntity<Resource> downloadFiles(@RequestParam("file-path") String filePath) {

        ByteArrayOutputStream byteArrayOutputStream = zipFileCreator.downloadZipFile(List.of(filePath));
        ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"minisign.zip\"");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    private ProcessResultResource mapToProcessResultResource(ProcessResult processResult) {
        return ProcessResultResource.builder()
                .processFeedback(processResult.getProcessFeedback())
                .exitedGraceful(processResult.isExitedGraceful())
                .processError(processResult.getProcessError())
                .exitValue(processResult.getExitValue())
                .createdFiles(processResult.getCreatedFiles())
                .build();
    }

    @SneakyThrows
    private byte[] convertToBytes(MultipartFile signedFile) {
        return signedFile.getBytes();
    }

}
