import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    alias(libs.plugins.publish.maven)
}

dependencies {
    implementation(libs.jackson.databind)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit)
    testImplementation(libs.assertj)
}

tasks.test {
    useJUnitPlatform()
}

configurations.matching { it.name.startsWith("dokka") }.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group.startsWith("com.fasterxml.jackson")) {
            // override jackson for Dokka as a workaround
            // see: https://github.com/Kotlin/dokka/issues/3472
            useVersion("2.15.3")
        }
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
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
    const val DESC = "Java Vite Core Library"
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
