# Vite Integration for JSP and Spring MVC

This project provides a custom JSP tag for integrating Vite-generated assets with JSP and Spring MVC
applications. It allows you to seamlessly import Vite-built JavaScript and CSS assets into your JSP
views, making it easier to modernize legacy web applications.

## Features

- Import Vite-built JavaScript and CSS assets into JSP views.
- Seamlessly switch between development and production environments.
- Customizable paths for the Vite manifest file and public resources.

## Getting Started

### Prerequisites

- Java 8 or higher
- Spring MVC
- Vite

### Installation

Add the following dependency to your Maven `pom.xml` file:

```xml

<dependency>
    <groupId>io.github.benny123tw</groupId>
    <artifactId>vite-spring-webmvc</artifactId>
    <version>1.0.0</version>
</dependency>
```

Or for Gradle:

```groovy
implementation 'io.github.benny123tw:vite-spring-webmvc:1.0.0'
```

### Configuration

Configure the `ViteProperties` in your Spring application configuration.

#### application.properties

```properties
vite.debug=true
# The path to the Vite manifest file
vite.manifestPath=/WEB-INF/dist/.vite/manifest.json
# The URL of the local Vite development server
vite.localServerUrl=http://localhost:5173
# The path to the public resources
vite.resourcePath=/resources
```

#### application.yaml

```yaml
vite:
    debug: true
    manifestPath: /WEB-INF/dist/.vite/manifest.json
    localServerUrl: http://localhost:5173
    resourcePath: /resources
```

### Usage

#### Configure Web MVC

Configure your Spring MVC application to handle resource paths:

```java

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.benny",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                value = Controller.class))
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/dist/");
    }

}
```

#### Using the `ViteImport` Tag in JSP

Add the taglib declaration to your JSP file and use the `vite:import` tag to include Vite assets:

```jsp
<%@ taglib uri="https://github.com/benny123tw/tags" prefix="vite" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Application</title>
    <vite:import entry="src/main.ts" />
</head>
<body>
    <h1>Welcome to My Application</h1>
</body>
</html>
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](../LICENSE) file for details.

## Acknowledgements

- [Spring Framework](https://spring.io/projects/spring-framework)
- [Vite](https://vitejs.dev/)
