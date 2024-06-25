import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    alias(libs.plugins.publish.maven)
}

group = "com.javite"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":javite-core"))

    implementation(libs.jre8.spring.core)
    implementation(libs.jre8.spring.webmvc)
    implementation(libs.jre8.spring.boot.starter)
    implementation(libs.jre8.spring.boot.starter.web)

    compileOnly(libs.jre8.javax.servlet)
    compileOnly(libs.jre8.javax.servlet.jsp)

    annotationProcessor(libs.jre8.spring.boot.configuration.processor)
}

tasks.jar {
    enabled = true
    archiveClassifier.set("")
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
            ),
        )
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}

object Meta {
    const val DESC = "Java Vite WebMVC Library for JRE 8"
    const val LICENSE = "MIT"
    const val LICENSE_URL = "https://opensource.org/licenses/mit"
    const val GITHUB_REPO = "benny123tw/javite"
    const val DEVELOPER_ID = "benny123tw"
    const val DEVELOPER_NAME = "Benny Yen"
    const val DEVELOPER_ORGANIZATION = "com.javite"
    const val DEVELOPER_ORGANIZATION_URL = "https://javite.com"
}

mavenPublishing {
    println("Publish ${project.group}, ${project.name}, ${project.version}")
    coordinates(project.group.toString(), project.name, project.version.toString())

    // Correctly configure JavaLibrary with Javadoc and sources JARs
    configure(
        JavaLibrary(
            javadocJar = JavadocJar.Javadoc(),
            sourcesJar = true
        )
    )

    pom {
        name.set(project.name)
        description.set(Meta.DESC)
        inceptionYear.set("2024")
        url.set("https://github.com/${Meta.GITHUB_REPO}")
        licenses {
            license {
                name.set(Meta.LICENSE)
                url.set(Meta.LICENSE_URL)
            }
        }
        developers {
            developer {
                id.set(Meta.DEVELOPER_ID)
                name.set(Meta.DEVELOPER_NAME)
                organization.set(Meta.DEVELOPER_ORGANIZATION)
                organizationUrl.set(Meta.DEVELOPER_ORGANIZATION_URL)
            }
        }
        scm {
            url.set("https://github.com/${Meta.GITHUB_REPO}.git")
            connection.set("scm:git:git://github.com/${Meta.GITHUB_REPO}.git")
            developerConnection.set("scm:git:git://github.com/${Meta.GITHUB_REPO}.git")
        }
        issueManagement {
            url.set("https://github.com/${Meta.GITHUB_REPO}/issues")
        }
    }
}

// gradle locking of dependency versions
//   *required+used for trivy scan
dependencyLocking {
    lockAllConfigurations()
}

// always run subproject task with parent
rootProject.tasks.dependencies { dependsOn(tasks.dependencies) }
