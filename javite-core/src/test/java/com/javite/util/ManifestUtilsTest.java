package com.javite.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class ManifestUtilsTest {

    private static final String MANIFEST_JSON = "{\"main.js\": {\"file\": \"main.js\", \"css\": [\"style.css\"]}}";

    @Test
    void testReadManifestFromInputStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(MANIFEST_JSON.getBytes(StandardCharsets.UTF_8));
        JsonNode manifest = ManifestUtils.readManifest(inputStream);

        assertThat(manifest.has("main.js")).isTrue();
        assertThat(manifest.get("main.js").get("file").asText()).isEqualTo("main.js");
        assertThat(manifest.get("main.js").get("css").get(0).asText()).isEqualTo("style.css");
    }

    @Test
    void testReadManifestFromFilePath() throws IOException {
        String tempFilePath = createTempManifestFile();
        JsonNode manifest = ManifestUtils.readManifest(tempFilePath);

        assertThat(manifest.has("main.js")).isTrue();
        assertThat(manifest.get("main.js").get("file").asText()).isEqualTo("main.js");
        assertThat(manifest.get("main.js").get("css").get(0).asText()).isEqualTo("style.css");
    }

    private String createTempManifestFile() throws IOException {
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("manifest", ".json");
        java.nio.file.Files.writeString(tempFile, ManifestUtilsTest.MANIFEST_JSON);
        return tempFile.toAbsolutePath().toString();
    }

}
