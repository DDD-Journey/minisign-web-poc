package org.dddjourney.minisignpocbackend.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MinisignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    void getMinisignVersion() {
        // when-then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/version"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("minisign 0.10"));

    }

    @Test
    @SneakyThrows
    void verifyFile() {
        // given
        MockMultipartFile signedFile = buildMockMultipartFile("signed-file", "src/test/resources/minisign/test_payload_file.txt");
        MockMultipartFile signatureFile = buildMockMultipartFile("signature-file", "src/test/resources/minisign/test_payload_file.txt.minisig");
        MockMultipartFile publicKeyFile = buildMockMultipartFile("public-key-file", "src/test/resources/minisign/minisign_public_key.pub");

        // when-then
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/verify-file")
                        .file(signedFile)
                        .file(signatureFile)
                        .file(publicKeyFile))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        "{\"exitValue\":0,"
                                + "\"exitedGraceful\":true,"
                                + "\"processFeedback\":\"Signature and comment signature verifiedTrusted comment: timestamp:1645981228\\tfile:test_payload_file.txt\\thashed\","
                                + "\"processError\":\"\"}"
                ));
    }

    private MockMultipartFile buildMockMultipartFile(String parameterName, String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile(parameterName, fileInputStream);
    }
}