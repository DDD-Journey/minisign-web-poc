package org.dddjourney.minisignpocbackend.infrastructure.openapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "open-api-doc.application")
public class OpenApiProperties {

    @NotNull
    private String name;

    @NotNull
    private String version;

    @NotNull
    private String description;

    @NotNull
    private String port;

}
