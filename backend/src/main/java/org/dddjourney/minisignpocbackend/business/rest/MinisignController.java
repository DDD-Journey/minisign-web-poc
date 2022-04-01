package org.dddjourney.minisignpocbackend.business.rest;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dddjourney.minisignpocbackend.business.domain.minisign.MinisignDownloadResult;
import org.dddjourney.minisignpocbackend.business.domain.MinisignService;
import org.dddjourney.minisignpocbackend.business.domain.minisign.MinisignProcessResult;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class MinisignController {

    private final MinisignService minisignService;

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


        MinisignProcessResult serviceResult = minisignService.verifyFile(
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

        MinisignProcessResult serviceResult = minisignService.signFile(
                password,
                convertToBytes(unsignedFile),
                convertToBytes(secretKeyFile),
                signatureFileName
        );

        return new ResponseEntity<>(mapToProcessResultResource(serviceResult), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:8082")
    @SneakyThrows
    @GetMapping(path = "download-files/{session-id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFiles(@PathVariable("session-id") String sessionId) {

        MinisignDownloadResult downloadResult = minisignService.downloadCreatedFiles(sessionId);

        HttpHeaders responseHeaders = new HttpHeaders();
        String headerValue = String.format("attachment; filename=\"%s\"", downloadResult.getFileName());
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .contentLength(downloadResult.getResource().contentLength())
                .body(downloadResult.getResource());
    }

    private ProcessResultResource mapToProcessResultResource(MinisignProcessResult serviceResult) {
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
