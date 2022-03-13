package org.dddjourney.minisignpocbackend.rest;

import lombok.RequiredArgsConstructor;
import org.dddjourney.minisignpocbackend.domain.MinisignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("minisign")
public class MinisignController {

    private final MinisignService minisignService;

    @GetMapping("/version")
    public ResponseEntity<String> getMinisignVersion() {
        return ResponseEntity.ok(minisignService.version());
    }
}
