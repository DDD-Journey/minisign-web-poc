package org.dddjourney.minisignpocbackend.business.rest;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.dddjourney.minisignpocbackend.business.domain.minisign.SessionCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(ZipFileExtractor.class)
class MinisignControllerTest {

    @MockBean
    SessionCreator sessionCreator;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ZipFileExtractor zipFileExtractor;

    String expectedSessionId;

    @BeforeEach
    void setUp() {
        expectedSessionId = UUID.randomUUID().toString();
        when(sessionCreator.createSessionId()).thenReturn(expectedSessionId);
    }

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
                        .file(publicKeyFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exitValue").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exitedGraceful").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.processError").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdFiles").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.processFeedback")
                        .value("Signature and comment signature verifiedTrusted comment: timestamp:1645981228\tfile:test_payload_file.txt\thashed"));

    }

    @Test
    @SneakyThrows
    void createKeys() {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/create-keys")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"password\": \"any_password!\", \"fileName\": \"create_key_test_case\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exitValue").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exitedGraceful").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.processError").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdFiles").isNotEmpty())
                .andReturn();

        String retrievedSessionId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.sessionId");
        assertThat(retrievedSessionId).isEqualTo(expectedSessionId);

        byte[] contentAsByteArray = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/download-files/{session-id}", expectedSessionId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"minisign_%s.zip\"", retrievedSessionId)))
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn().getResponse().getContentAsByteArray();

        List<ZipFileExtractor.FileMetaData> fileMetaDataList = zipFileExtractor.extractMetaData(contentAsByteArray);

        assertThat(fileMetaDataList).extracting("fileName").containsExactlyInAnyOrder(
                "create_key_test_case.key",
                "create_key_test_case.pub"
        );
    }

    @Test
    @SneakyThrows
    void signFile() {
        // given
        MockMultipartFile unsignedFile = buildMockMultipartFile("unsigned-file", "src/test/resources/minisign/test_payload_file.txt");
        MockMultipartFile secretKeyFile = buildMockMultipartFile("secret-key-file", "src/test/resources/minisign/minisign_secret_key.key");
        Map<String, List<String>> params = Map.of(
                "password", List.of("test123"),
                "signature-file-name", List.of("test_payload_file.txt.minisig")
        );

        // when-then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/sign-file")
                        .file(unsignedFile)
                        .file(secretKeyFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .params(CollectionUtils.toMultiValueMap(params)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exitValue").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exitedGraceful").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.processError").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdFiles").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.processFeedback").value("Password: Deriving a key from the password and decrypting the secret key... done"))
                .andReturn();

        // and then
        String retrievedSessionId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.sessionId");
        assertThat(retrievedSessionId).isEqualTo(expectedSessionId);

        byte[] contentAsByteArray = mockMvc.perform(MockMvcRequestBuilders
                        .get("/download-files/{session-id}", expectedSessionId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", "test_payload_file.txt.minisig")))
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn().getResponse().getContentAsByteArray();

        String fileContent = new String(contentAsByteArray);
        assertThat(fileContent).contains("untrusted comment: signature from minisign secret key\n" +
                "RUQxzvHkRbQZ5bQCPbXARQnzqO5BBu/la4iBk7OraK3qHq6tLdGmHfwStMOba1ssqElwM2FeUMm58mMc4mH1ScYvpZ13d900Sgs=");
    }

    @Test
    @SneakyThrows
    void whenDownloadFilesWithWrongSessionIdThenNotFound() {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/download-files")
                        .param("session-id", "ANY_WRONG_SESSION_ID"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private MockMultipartFile buildMockMultipartFile(String parameterName, String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile(parameterName, fileInputStream);
    }
}