package org.dddjourney.minisignpocbackend.business.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

@Data
@Builder
public class MinisignDownloadResult {

    private String sessionId;
    private ByteArrayResource resource;
    private String fileName;
}
