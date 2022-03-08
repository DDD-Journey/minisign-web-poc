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

    @Test
    void verifyFile() {
        String output = subject.verify();
        assertThat(output).contains("Signature and comment signature verified");
    }

    @Test
    void signFile() {
        String output = subject.sign("test123");
    }
}