include("plugin")

include(
//    ":sample-kotlin",
//    ":sample-groovy",
//    ":sample-aar" // For uncomment should get error at sync project time as well;
)

pluginManagement {

    val rustorePublish = "0.2.0-SNAPSHOT"

    resolutionStrategy {
        eachPlugin {
            if(requested.id.namespace == "ru.cian") {
                useModule("ru.cian.rustore-plugin:plugin:${rustorePublish}")
            }
        }
    }

    repositories {
        mavenLocal()
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

plugins {
    id("com.gradle.enterprise") version("3.13.2")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        // To publish report add `-Pscan` to build command;
        publishAlwaysIf(settings.extra.has("scan"))
    }
}
