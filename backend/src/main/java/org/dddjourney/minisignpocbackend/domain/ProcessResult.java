package org.dddjourney.minisignpocbackend.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class ProcessResult {

    private int exitValue;
    private boolean exitedGraceful;
    private String processFeedback;
    private String processError;
    @Singular
    private List<String> createdFiles;


}
