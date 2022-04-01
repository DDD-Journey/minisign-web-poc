package org.dddjourney.minisignpocbackend.business.domain.minisign;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class MinisignProcessResult {

    private String sessionId;
    private int exitValue;
    private boolean exitedGraceful;
    private String processFeedback;
    private String processError;
    @Singular
    private List<String> createdFiles;
}
