package org.dddjourney.minisignpocbackend.infrastructure.process;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.dddjourney.minisignpocbackend.business.domain.minisign.MinisignResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        InternalMinisignResult minisignResult = subject.version();

        // then
        assertThat(minisignResult.getProcessFeedback()).contains("minisign 0.10");
        assertThat(minisignResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(minisignResult.isExitedGraceful()).isTrue();
    }

    @Test
    void verifyFile() {
        // given
        String signedFile = "src/test/resources/minisign/test_payload_file.txt";
        String signatureFile = "src/test/resources/minisign/test_payload_file.txt.minisig";
        String publicKeyFile = "src/test/resources/minisign/minisign_public_key.pub";

        // when
        InternalMinisignResult minisignResult = subject.verifyFile(signedFile, signatureFile, publicKeyFile);

        // then
        assertThat(minisignResult.getProcessFeedback()).contains("Signature and comment signature verified");
        assertThat(minisignResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(minisignResult.isExitedGraceful()).isTrue();
    }

    @Test
    @SneakyThrows
    void signFile(@TempDir Path tempDir) {
        // given
        String payloadFile = "src/test/resources/minisign/test_payload_file.txt";
        String secretKeyFile = "src/test/resources/minisign/minisign_secret_key.key";
        File signatureFile = buildFile(tempDir, "test_payload_file.txt.minisig");

        // when
        InternalMinisignResult minisignResult = subject.signFile(TEST_PASSWORD, payloadFile, secretKeyFile, signatureFile);

        // then
        assertThat(minisignResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(minisignResult.isExitedGraceful()).isTrue();
        assertThat(readFile(signatureFile)).contains("file:test_payload_file.txt");
        assertThat(minisignResult.getProcessFeedback()).isEqualTo("Password: Deriving a key from the password and decrypting the secret key... done");
    }

    @Test
    void createKeys(@TempDir Path tempDir) {
        // given
        String pubKeyFileName = "test_key_file.pub";
        String secretKeyFileName = "test_key_file.key";
        File pubKeyFile = buildFile(tempDir, pubKeyFileName);
        File secretKeyFile = buildFile(tempDir, secretKeyFileName);

        // when
        InternalMinisignResult minisignResult = subject.createKeys(pubKeyFile, "test654!", secretKeyFile);

        // then
        assertThat(minisignResult.getExitValue()).isEqualTo(EXIT_VALUE_SUCCESS);
        assertThat(minisignResult.isExitedGraceful()).isTrue();
        assertThat(minisignResult.getCreatedFiles())
                .extracting(File::getName)
                .containsExactlyInAnyOrder(pubKeyFileName, secretKeyFileName);
    }

    private File buildFile(Path tempDir, String fileName) {
        return Paths.get(tempDir.toString() , fileName).toFile();
    }

    private String readFile(File signatureFile) throws IOException {
        return FileUtils.readFileToString(signatureFile, StandardCharsets.UTF_8);
    }
}