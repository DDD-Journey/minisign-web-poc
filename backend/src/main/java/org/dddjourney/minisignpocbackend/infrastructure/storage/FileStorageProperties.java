package org.dddjourney.minisignpocbackend.infrastructure.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.file-storage")
public class FileStorageProperties {

    private String uploadFolder;
    private String downloadFolder;

}
