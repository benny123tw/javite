package io.github.benny123tw.servlet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

/**
 * Provides an out-of-the-box configuration for Vite.
 */
@Configuration
@ComponentScan(basePackages = "io.github.benny123tw.servlet.config")
public class ViteConfig {

    @Value("${vite.debug:true}")
    private boolean debug;

    @Value("${vite.manifestPath:/WEB-INF/dist/.vite/manifest.json}")
    private String manifestPath;

    @Value("${vite.localServerUrl:http://localhost:5173}")
    private String localServerUrl;

    @Value("${vite.resourcePath:/resources}")
    private String resourcePath;

    @Bean
    public ViteProperties viteProperties() {
        ViteProperties viteProperties = new ViteProperties();
        viteProperties.setDebug(debug);
        viteProperties.setManifestPath(manifestPath);
        viteProperties.setLocalServerUrl(localServerUrl);
        viteProperties.setResourcePath(resourcePath);
        return viteProperties;
    }
}
