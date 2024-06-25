/**
 * Provides an out-of-the-box configuration for integrating Vite with Spring MVC applications.
 *
 * <p>This configuration class defines beans and properties required for Vite integration, which can be customized
 * through the application's {@code application.properties} file.</p>
 *
 * <p>The following properties can be configured:</p>
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
package com.javite.spring.config;
