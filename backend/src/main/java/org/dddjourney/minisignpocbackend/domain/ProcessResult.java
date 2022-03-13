package org.dddjourney.minisignpocbackend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessResult {

    private int exitValue;
    private boolean exitedGraceful;
    private String processFeedback;
    private String processError;


}
