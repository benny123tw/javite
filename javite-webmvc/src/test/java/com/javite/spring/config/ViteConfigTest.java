package com.javite.spring.config;

import com.javite.config.ViteProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ViteConfigTest.TestConfig.class)
public class ViteConfigTest {

    @Value("${vite.debug:true}")
    private boolean debug;

    @Value("${vite.manifestPath:/WEB-INF/dist/.vite/manifest.json}")
    private String manifestPath;

    @Value("${vite.localServerUrl:http://localhost:5173}")
    private String localServerUrl;

    @Value("${vite.resourcePath:/resources}")
    private String resourcePath;

    @Test
    void testVitePropertiesBean() {
        ViteProperties viteProperties = viteProperties();
        assertThat(viteProperties.isDebug()).isEqualTo(debug);
        assertThat(viteProperties.getManifestPath()).isEqualTo(manifestPath);
        assertThat(viteProperties.getLocalServerUrl()).isEqualTo(localServerUrl);
        assertThat(viteProperties.getResourcePath()).isEqualTo(resourcePath);
    }

    @Bean
    public ViteProperties viteProperties() {
        ViteProperties viteProperties = new ViteProperties();
        viteProperties.setDebug(debug);
        viteProperties.setManifestPath(manifestPath);
        viteProperties.setLocalServerUrl(localServerUrl);
        viteProperties.setResourcePath(resourcePath);
        return viteProperties;
    }

    @org.springframework.context.annotation.Configuration
    static class TestConfig {
        @Bean
        public ViteConfig viteConfig() {
            return new ViteConfig();
        }
    }
}
