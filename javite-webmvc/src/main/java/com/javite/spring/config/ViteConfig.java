package com.javite.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.javite.config.ViteProperties;

/**
 * Configuration class for integrating Vite with Spring MVC applications.
 *
 * <p>This class defines beans for Vite properties that can be customized through the application's
 * {@code application.properties} file.</p>
 */
@Configuration
public class ViteConfig {

    @Value("${vite.debug:true}")
    private boolean debug;

    @Value("${vite.manifestPath:/WEB-INF/dist/.vite/manifest.json}")
    private String manifestPath;

    @Value("${vite.localServerUrl:http://localhost:5173}")
    private String localServerUrl;

    @Value("${vite.resourcePath:/resources}")
    private String resourcePath;

    /**
     * Creates a {@link ViteProperties} bean configured with the application's Vite settings.
     *
     * @return a configured {@link ViteProperties} instance
     */
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
