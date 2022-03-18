package org.dddjourney.minisignpocbackend.rest;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProcessResultResource {

    private int exitValue;
    private boolean exitedGraceful;
    private String processFeedback;
    private String processError;
    private List<String> createdFiles;

}
