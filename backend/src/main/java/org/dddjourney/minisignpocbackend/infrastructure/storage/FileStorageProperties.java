package org.dddjourney.minisignpocbackend.infrastructure.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.file-storage")
public class FileStorageProperties {

    private String uploadFolder;
    private String downloadFolder;

}
