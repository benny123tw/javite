package io.github.benny123tw.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import io.github.benny123tw.servlet.config.ViteProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
public class VitePropertiesTest {

    private final ViteProperties viteProperties;

    @Autowired
    public VitePropertiesTest(ViteProperties viteProperties) {
        this.viteProperties = viteProperties;
    }

    @Test
    public void testViteProperties() {
        assertFalse(viteProperties.isDebug());
        assertEquals("/.vite/manifest.json", viteProperties.getManifestPath());
        assertEquals("https://github.com/benny123tw", viteProperties.getLocalServerUrl());
    }
}
