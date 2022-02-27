package org.dddjourney.minisignpocbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinisignRunnerTest {

    MinisignRunner subject;

    @BeforeEach
    void setUp() {
        subject = new MinisignRunner();
    }

    @Test
    void runVersion() {
        String version = subject.version();
        assertThat(version).contains("minisign 0.10");
    }
}