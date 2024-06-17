package io.github.benny123tw.servlet.annotation;

import io.github.benny123tw.servlet.config.ViteConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ViteConfig.class)
public @interface EnableVite {

}
