package com.javite.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class VitePropertiesTest {

    @Test
    void testGettersAndSetters() {
        ViteProperties properties = new ViteProperties();

        properties.setDebug(false);
        properties.setManifestPath("/.vite/manifest.json");
        properties.setLocalServerUrl("http://localhost:5173");
        properties.setResourcePath("/resources");

        assertThat(properties.isDebug()).isFalse();
        assertThat(properties.getManifestPath()).isEqualTo("/.vite/manifest.json");
        assertThat(properties.getLocalServerUrl()).isEqualTo("http://localhost:5173");
        assertThat(properties.getResourcePath()).isEqualTo("/resources");
    }

}
