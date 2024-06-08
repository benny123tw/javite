object Meta {
    const val RELEASE = "https://s01.oss.sonatype.org/service/local/"
    const val SNAPSHOT = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    const val DESC = "OSS GitHub Java Library Template Repository"
    const val LICENSE = "MIT"
    const val LICENSE_URL = "https://opensource.org/licenses/mit"
    const val GITHUB_REPO = "benny123tw/vite-integration"
    const val DEVELOPER_ID = "benny123tw"
    const val DEVELOPER_NAME = "Benny Yen"
    const val DEVELOPER_ORGANIZATION = "io.github.benny123tw"
    const val DEVELOPER_ORGANIZATION_URL = "https://www.github.com/benny123tw"
}

plugins {
    `java-library`
    `maven-publish`
    signing
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

description = "Vite - Spring Web MVC"

dependencies {
    implementation(libs.spring.core)
    implementation(libs.spring.webmvc)
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.jackson.databind)
    implementation(libs.jakarta.servlet)
    implementation(libs.jakarta.servlet.jsp)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit)
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}

signing {
    val signingKey = providers.environmentVariable("GPG_SIGNING_KEY")
    val signingPassphrase = providers.environmentVariable("GPG_SIGNING_PASSPHRASE")
    if (signingKey.isPresent && signingPassphrase.isPresent) {
        useInMemoryPgpKeys(signingKey.get(), signingPassphrase.get())
        val extension = extensions.getByName("publishing") as PublishingExtension
        sign(extension.publications)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])
            pom {
                name.set(project.name)
                description.set(Meta.DESC)
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
    }
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
            ),
        )
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

// gradle locking of dependency versions
//   *required+used for trivy scan
dependencyLocking {
    lockAllConfigurations()
}
// always run subproject task with parent
rootProject.tasks.dependencies { dependsOn(tasks.dependencies) }
