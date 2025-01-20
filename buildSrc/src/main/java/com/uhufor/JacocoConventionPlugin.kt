package com.uhufor

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.pluginManager.apply("jacoco")

        project.extensions.configure<JacocoPluginExtension> {
            toolVersion = JACOCO_VERSION
        }

        project.tasks.withType<Test>().configureEach {
            extensions.configure<org.gradle.testing.jacoco.plugins.JacocoTaskExtension> {
                isIncludeNoLocationClasses = true
                excludes = listOf("jdk.internal.*")
            }
        }

        project.tasks.register<JacocoReport>("jacocoTestReport") {
            dependsOn(project.tasks.withType<Test>())

            group = "Reporting"
            description = "Generate Jacoco coverage reports after running tests."

            reports {
                xml.required.set(true)
                html.required.set(true)
                csv.required.set(false)
                html.outputLocation.set(project.layout.buildDirectory.dir("reports/jacoco/html"))
            }

            val fileFilter = listOf(
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*"
            )

            val debugTree = project.fileTree(
                project.layout.buildDirectory.dir("intermediates/javac/debug").get().asFile
            ) {
                exclude(fileFilter)
            }

            val kotlinDebugTree = project.fileTree(
                project.layout.buildDirectory.dir("tmp/kotlin-classes/debug").get().asFile
            ) {
                exclude(fileFilter)
            }

            classDirectories.setFrom(project.files(debugTree, kotlinDebugTree))
            sourceDirectories.setFrom(project.files("src/main/java", "src/main/kotlin"))
            executionData.setFrom(
                project.files(
                    project.layout.buildDirectory.file("jacoco/testDebugUnitTest.exec").get().asFile
                )
            )

            additionalSourceDirs.setFrom(project.files("src/main/java", "src/main/kotlin"))
            additionalClassDirs.setFrom(project.files(debugTree, kotlinDebugTree))
        }

        project.tasks.named("check") {
            dependsOn("jacocoTestReport")
        }
    }

    companion object {
        const val JACOCO_VERSION = "0.8.12"
    }
}
