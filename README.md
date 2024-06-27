# JaVite

Seamlessly Integrate Vite with Your Java Web Applications for a Modern Development Experience

### Introduction

JaVite is a module designed to integrate Vite with Java web applications, focusing on legacy Spring
MVC projects and Thymeleaf templates. It allows developers to modernize their front-end assets using
Vite's powerful bundling capabilities while seamlessly incorporating these assets into traditional
Java web applications.

### Features

* Custom JSP tags for importing Vite-generated assets
* Utility functions for managing asset imports
* Support for both development and production environments
* Configuration classes for seamless integration
* Compatibility with legacy Spring MVC projects and Thymeleaf templates
* Monorepo structure to support multiple modules and packages

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
