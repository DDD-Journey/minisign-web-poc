package org.dddjourney.minisignpocbackend;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;

public class MinisignRunner {

    @SneakyThrows
    public String version() {
        String[] args = new String[]{"minisign", "-v"};
        Process process = new ProcessBuilder(args).start();
        return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
    }
}
