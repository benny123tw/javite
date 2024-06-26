package com.javite.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Provides an out-of-the-box configuration for integrating Vite with Spring MVC applications.
 *
 * <p>This configuration class automatically scans the package {@code io.github.benny123tw.servlet.config}
 * for components and provides the necessary beans and properties to enable Vite integration.</p>
 *
 * <p>The following properties can be configured in the application's {@code application.properties} file:</p>
 * <ul>
 *     <li>{@code vite.debug} - Enables or disables debug mode for Vite integration. Default is {@code true}.</li>
 *     <li>{@code vite.manifestPath} - Specifies the path to the Vite manifest file. Default is {@code /WEB-INF/dist/.vite/manifest.json}.</li>
 *     <li>{@code vite.localServerUrl} - Specifies the URL of the local Vite development server. Default is {@code http://localhost:5173}.</li>
 *     <li>{@code vite.resourcePath} - Specifies the path to Vite resources. Default is {@code /resources}.</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre class="code">
 * &#64;Configuration
 * &#64;EnableVite
 * public class AppConfig {
 *     // Your other Spring configuration
 * }
 * </pre>
 *
 * @see com.javite.spring.annotation.EnableVite
 */
@Configuration
@ComponentScan(basePackages = "com.javite.spring.config")
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
