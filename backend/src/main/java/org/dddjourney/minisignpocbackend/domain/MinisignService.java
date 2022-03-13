package org.dddjourney.minisignpocbackend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinisignService {

    private final MinisignRunner minisignRunner;

    public String version() {
        return minisignRunner.version().getProcessFeedback();
    }
}
