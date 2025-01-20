// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val jacocoVersion by extra("0.8.12")
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
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
