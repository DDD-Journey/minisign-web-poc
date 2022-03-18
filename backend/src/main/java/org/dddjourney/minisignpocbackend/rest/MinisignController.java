package org.dddjourney.minisignpocbackend.rest;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dddjourney.minisignpocbackend.domain.MinisignService;
import org.dddjourney.minisignpocbackend.domain.ProcessResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class MinisignController {

    private final MinisignService minisignService;

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

    private ProcessResultResource mapToProcessResultResource(ProcessResult processResult) {
        return ProcessResultResource.builder()
                .processFeedback(processResult.getProcessFeedback())
                .exitedGraceful(processResult.isExitedGraceful())
                .processError(processResult.getProcessError())
                .exitValue(processResult.getExitValue())
                .build();
    }


    @SneakyThrows
    private byte[] convertToBytes(MultipartFile signedFile) {
        return signedFile.getBytes();
    }

}
