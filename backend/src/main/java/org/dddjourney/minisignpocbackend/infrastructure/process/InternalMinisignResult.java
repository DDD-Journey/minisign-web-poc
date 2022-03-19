package org.dddjourney.minisignpocbackend.infrastructure.process;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.dddjourney.minisignpocbackend.business.domain.MinisignResult;

import java.io.File;
import java.util.List;

@Data
@Builder
public class InternalMinisignResult implements MinisignResult {

    private int exitValue;
    private boolean exitedGraceful;
    private String processFeedback;
    private String processError;
    @Singular
    private List<File> createdFiles;
}
