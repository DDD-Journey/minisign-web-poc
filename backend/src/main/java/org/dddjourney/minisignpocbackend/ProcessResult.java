package org.dddjourney.minisignpocbackend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessResult {

    private int exitValue;
    private String output;
    private boolean exitedGraceful;


}
