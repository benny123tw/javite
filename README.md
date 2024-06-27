<p align="center">
  <a href="https://javite.com" target="_blank" rel="noopener noreferrer">
    <img width="60" src="https://javite.com/Duke.svg" alt="JaVite logo">
  </a>
</p>
<br/>
<p align="center">
  <a href="https://central.sonatype.com/artifact/com.javite/javite-webmvc"><img src="https://img.shields.io/maven-central/v/com.javite/javite-webmvc.svg" alt="Maven Central"></a>
  <a href="https://github.com/benny123tw/javite/actions/workflows/1.pipeline.yml"><img src="https://github.com/benny123tw/javite/actions/workflows/1.pipeline.yml/badge.svg?branch=main" alt="Build Status"></a>
  <a href="https://benny123tw.github.io/javite/docs/current"><img src="https://img.shields.io/badge/JavaDoc-Online-green" alt="Javadoc"></a>
</p>
<br/>

# JaVite ⚡

> Seamlessly Integrate Vite with Your Java Web Applications

- 🌐 Custom JSP tags for Vite assets
- ⚙️ Asset import utilities
- 🔄 Dev and prod support
- 📁 Seamless configuration
- 💻 Spring MVC (JSP & Thymeleaf) support

JaVite (pronounced "jah-vite") is a module designed to integrate Vite with Java web applications,
focusing on legacy Spring MVC projects and Thymeleaf templates. It allows developers to modernize
their front-end assets using Vite's powerful bundling capabilities while seamlessly incorporating
these assets into traditional Java web applications.

### Installation

To include JaVite in your project, add the following dependency to your `build.gradle` file:

```groovy
dependencies {
    implementation 'com.javite:javite-webmvc:0.1.3'
}
```

For Maven, add the following to your `pom.xml`:

```xml
<dependency>
    <groupId>com.javite</groupId>
    <artifactId>javite-webmvc</artifactId>
    <version>0.1.3</version>
</dependency>
```

### Usage

#### JSP Integration

1. Add the custom tag to your JSP files:

    ```jsp
    <%@ taglib prefix="vite" uri="https://javite.com/tags" %>
    ```

2. Use the custom tags to import Vite-generated assets:

    ```jsp
    <vite:import entry="/main.ts"/>
    ```

### Configuration

JaVite uses a configuration class to determine the environment and asset paths. By default, it looks
for your `application.properties` (or `application.yml`) file:

```properties
vite.debug=true
vite.manifest-path=/WEB-INF/dist/.vite/manifest.json
vite.local-server-url=http://localhost:5173
vite.resource-path=/resources
```

### Contributing

Contributions are welcome! Please submit a pull request or open an issue for any bugs or feature
requests.

### License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

### Acknowledgements

This project was inspired by the following resources:

- [Java Library Template](https://github.com/thriving-dev/java-library-template)
- [Spring Framework](https://spring.io/projects/spring-framework)
- [Vite](https://vitejs.dev/)
- [Gradle](https://gradle.org/)

For more detailed documentation, please visit [JaVite Documentation](https://javite.com/).
