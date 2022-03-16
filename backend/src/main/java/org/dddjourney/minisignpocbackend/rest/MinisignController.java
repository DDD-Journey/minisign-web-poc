package org.dddjourney.minisignpocbackend.rest;

import lombok.RequiredArgsConstructor;
import org.dddjourney.minisignpocbackend.domain.MinisignService;
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

//    @PostMapping("/uploadFile")
//    public ResponseEntity<String> uploadFile(
//            @RequestParam("payloadFile") MultipartFile payloadFile,
//            @RequestParam("publicKeyFile") MultipartFile publicKeyFile) {
//
//
//        return new ResponseEntity("Upload successful", HttpStatus.CREATED);
//    }

}
