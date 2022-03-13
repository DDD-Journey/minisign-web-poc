package org.dddjourney.minisignpocbackend;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.dddjourney.minisignpocbackend.domain.MinisignRunner;
import org.dddjourney.minisignpocbackend.domain.ProcessResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class MinisignRunnerTest {

    static final String TEST_PASSWORD = "test123";
    static final int EXIT_VALUE_SUCCESS = 0;

    MinisignRunner subject;

    @BeforeEach
    void setUp() {
        subject = new MinisignRunner();
    }

    @Test
    void runVersion() {
        // when
        ProcessResult processResult = subject.version();

        // then
        assertThat(processResult.getProcessFeedback()).contains("minisign 0.10");
        assertThat(processResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(processResult.isExitedGraceful()).isTrue();
    }

    @Test
    void verifyFile() {
        // given
        String payloadFile = "src/test/resources/minisign/test_payload_file.txt";
        String publicKeyFile = "src/test/resources/minisign/minisign_public_key.pub";

        // when
        ProcessResult processResult = subject.verifyFile(payloadFile, publicKeyFile);

        // then
        assertThat(processResult.getProcessFeedback()).contains("Signature and comment signature verified");
        assertThat(processResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(processResult.isExitedGraceful()).isTrue();
    }

    @Test
    @SneakyThrows
    void signFile(@TempDir Path tempDir) {
        // given
        String payloadFile = "src/test/resources/minisign/test_payload_file.txt";
        String secretKeyFile = "src/test/resources/minisign/minisign_secret_key.key";
        String signatureFile = buildFilePathString(tempDir, "test_payload_file.txt.minisig");

        // when
        ProcessResult processResult = subject.signFile(TEST_PASSWORD, payloadFile, secretKeyFile, signatureFile);

        // then
        assertThat(processResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(processResult.isExitedGraceful()).isTrue();
        assertThat(readFile(signatureFile)).contains("file:test_payload_file.txt");
        assertThat(processResult.getProcessFeedback()).isEqualTo("Password: Deriving a key from the password and decrypting the secret key... done");
    }

    private String buildFilePathString(Path tempDir, String fileName) {
        return tempDir.toString() + "/" + fileName;
    }

    private String readFile(String signatureFile) throws IOException {
        return FileUtils.readFileToString(new File(signatureFile), StandardCharsets.UTF_8);
    }
}