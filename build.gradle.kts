// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val jacocoVersion by extra("0.8.12")
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    id("org.sonarqube") version "4.3.0.3225"
}

subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("jacoco")) {
            extensions.configure<JacocoPluginExtension> {
                toolVersion = rootProject.extra["jacocoVersion"] as String
            }
        }
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "uhufor_udf_sample")
        property("sonar.organization", "uhufor")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.token", "64d7b040973568b17085a0138c3f021af532af9d")

        property("sonar.sources", "src/main/java, src/main/kotlin")
        property("sonar.tests", "src/test/java, src/test/kotlin")
        property("sonar.exclusions", "**/*.gradle.kts, **/build/**, **/generated/**")


        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            listOf(
                "${project.projectDir}/app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
            )
        )
    }
}
