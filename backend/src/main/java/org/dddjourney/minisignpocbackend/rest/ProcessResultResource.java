package org.dddjourney.minisignpocbackend.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessResultResource {

    private int exitValue;
    private boolean exitedGraceful;
    private String processFeedback;
    private String processError;

}
