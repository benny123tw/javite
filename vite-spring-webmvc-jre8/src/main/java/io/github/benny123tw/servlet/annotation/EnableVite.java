package io.github.benny123tw.servlet.annotation;

import io.github.benny123tw.servlet.config.ViteConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Annotation to enable Vite integration in a Spring MVC application.
 *
 * <p>When this annotation is applied to a {@code @Configuration} class, it imports the
 * {@link ViteConfig} configuration, which sets up the necessary beans and configuration
 * for using Vite in the application.</p>
 *
 * <p>Usage:</p>
 * <pre class="code">
 * &#64;Configuration
 * &#64;EnableVite
 * public class AppConfig {
 *     // Your other Spring configuration
 * }
 * </pre>
 *
 * <p>This annotation is typically applied at the top-level configuration class to ensure
 * that Vite's configuration is loaded and available to the application context.</p>
 *
 * @see io.github.benny123tw.servlet.config.ViteConfig
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ViteConfig.class)
public @interface EnableVite {

}
