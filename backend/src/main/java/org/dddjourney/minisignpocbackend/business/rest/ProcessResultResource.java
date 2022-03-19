package org.dddjourney.minisignpocbackend.business.rest;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProcessResultResource {

    private String sessionId;
    private int exitValue;
    private boolean exitedGraceful;
    private String processFeedback;
    private String processError;
    private List<String> createdFiles;

}
