import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    alias(libs.plugins.publish.maven)
}

description = "Vite Integration"

allprojects {
    group = "io.github.benny123tw"
    repositories {
        mavenCentral()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}

object Meta {
    const val RELEASE = "https://s01.oss.sonatype.org/service/local/"
    const val SNAPSHOT = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    const val DESC = "Vite integration with Spring Web MVC."
    const val LICENSE = "MIT"
    const val LICENSE_URL = "https://opensource.org/licenses/mit"
    const val GITHUB_REPO = "benny123tw/vite-integration"
    const val DEVELOPER_ID = "benny123tw"
    const val DEVELOPER_NAME = "Benny Yen"
    const val DEVELOPER_ORGANIZATION = "io.github.benny123tw"
    const val DEVELOPER_ORGANIZATION_URL = "https://www.github.com/benny123tw"
}

mavenPublishing {
    println("Publish ${project.group.toString()}, ${project.name}, ${project.version.toString()}")
    coordinates(project.group.toString(), project.name, project.version.toString())

    pom {
        name.set(project.name)
        description.set(Meta.DESC)
        inceptionYear.set("2024")
        url.set(Meta.GITHUB_REPO)
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
