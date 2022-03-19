package org.dddjourney.minisignpocbackend.infrastructure.process;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class InternalMinisignProcessTest {

    static final String TEST_PASSWORD = "test123";
    static final int EXIT_VALUE_SUCCESS = 0;

    InternalMinisignProcess subject;

    @BeforeEach
    void setUp() {
        subject = new InternalMinisignProcess();
    }

    @Test
    void runVersion() {
        // when
        InternalMinisignResult internalMinisignResult = subject.version();

        // then
        assertThat(internalMinisignResult.getProcessFeedback()).contains("minisign 0.10");
        assertThat(internalMinisignResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(internalMinisignResult.isExitedGraceful()).isTrue();
    }

    @Test
    void verifyFile() {
        // given
        String signedFile = "src/test/resources/minisign/test_payload_file.txt";
        String signatureFile = "src/test/resources/minisign/test_payload_file.txt.minisig";
        String publicKeyFile = "src/test/resources/minisign/minisign_public_key.pub";

        // when
        InternalMinisignResult internalMinisignResult = subject.verifyFile(signedFile, signatureFile, publicKeyFile);

        // then
        assertThat(internalMinisignResult.getProcessFeedback()).contains("Signature and comment signature verified");
        assertThat(internalMinisignResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(internalMinisignResult.isExitedGraceful()).isTrue();
    }

    @Test
    @SneakyThrows
    void signFile(@TempDir Path tempDir) {
        // given
        String payloadFile = "src/test/resources/minisign/test_payload_file.txt";
        String secretKeyFile = "src/test/resources/minisign/minisign_secret_key.key";
        String signatureFile = buildFilePathString(tempDir, "test_payload_file.txt.minisig");

        // when
        InternalMinisignResult internalMinisignResult = subject.signFile(TEST_PASSWORD, payloadFile, secretKeyFile, signatureFile);

        // then
        assertThat(internalMinisignResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(internalMinisignResult.isExitedGraceful()).isTrue();
        assertThat(readFile(signatureFile)).contains("file:test_payload_file.txt");
        assertThat(internalMinisignResult.getProcessFeedback()).isEqualTo("Password: Deriving a key from the password and decrypting the secret key... done");
    }

    private String buildFilePathString(Path tempDir, String fileName) {
        return tempDir.toString() + "/" + fileName;
    }

    private String readFile(String signatureFile) throws IOException {
        return FileUtils.readFileToString(new File(signatureFile), StandardCharsets.UTF_8);
    }
}