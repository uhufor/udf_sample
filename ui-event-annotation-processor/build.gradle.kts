plugins {
    alias(libs.plugins.java.library)
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(project(":ui-event-annotation"))

    implementation(libs.ksp.api)
    implementation(libs.kotlinpoet.ksp)
}
