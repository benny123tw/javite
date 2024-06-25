import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    alias(libs.plugins.publish.maven)
    alias(libs.plugins.dokka)
    kotlin("jvm") version "2.0.0" apply false
}

description = "Java library for Vite integration."
group = "io.github.benny123tw"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
        dokkaSourceSets {
            configureEach {
                sourceRoots.from(file("src/main/java"))
            }
        }
    }

}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}

object Meta {
    const val DESC = "Java library for Vite integration."
    const val LICENSE = "MIT"
    const val LICENSE_URL = "https://opensource.org/licenses/mit"
    const val GITHUB_REPO = "benny123tw/javite"
    const val DEVELOPER_ID = "benny123tw"
    const val DEVELOPER_NAME = "Benny Yen"
    const val DEVELOPER_ORGANIZATION = "io.github.benny123tw"
    const val DEVELOPER_ORGANIZATION_URL = "https://www.github.com/benny123tw"
}

mavenPublishing {
    println("Publish ${project.group}, ${project.name}, ${project.version}")
    coordinates(project.group.toString(), project.name, project.version.toString())

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
