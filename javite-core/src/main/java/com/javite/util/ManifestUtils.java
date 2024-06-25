package com.javite.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility methods for reading and processing Vite manifest files.
 *
 * <p>This class provides static methods to read and parse the Vite manifest file,
 * which maps entry points to the corresponding generated assets. These methods
 * can be used to retrieve information about the assets for inclusion in web pages.</p>
 *
 * @author Benny Yen
 * @since 0.1.0
 */
public class ManifestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads the Vite manifest file and returns its contents as a {@link JsonNode}.
     *
     * @param manifestFilePath the path to the manifest file
     * @return the parsed manifest file as a {@link JsonNode}
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static JsonNode readManifest(String manifestFilePath) throws IOException {
        File file = new File(manifestFilePath);
        return objectMapper.readTree(file);
    }

    /**
     * Reads the Vite manifest file from an {@link InputStream} and returns its contents as a {@link JsonNode}.
     *
     * @param manifestStream the input stream of the manifest file
     * @return the parsed manifest file as a {@link JsonNode}
     * @throws IOException if an I/O error occurs while reading the stream
     */
    public static JsonNode readManifest(InputStream manifestStream) throws IOException {
        return objectMapper.readTree(manifestStream);
    }

}
