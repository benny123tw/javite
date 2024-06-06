package io.github.benny123tw.servlet;

import io.github.benny123tw.servlet.config.ViteProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TestApplication.class)
public class ViteDefaultPropertiesTest {

    private final ViteProperties viteProperties;

    @Autowired
    public ViteDefaultPropertiesTest(ViteProperties viteProperties) {
        this.viteProperties = viteProperties;
    }

    @Test
    public void testViteDefaultProperties() {
        assertTrue(viteProperties.isDebug());
        assertEquals("/WEB-INF/dist/.vite/manifest.json", viteProperties.getManifestPath());  // Default value
        assertEquals("http://localhost:5173", viteProperties.getLocalServerUrl());  // Default value
    }
}
