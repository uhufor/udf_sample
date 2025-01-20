package com.uhufor

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarQubeExtension

class SonarCloudConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("org.sonarqube")

        project.extensions.configure<SonarQubeExtension> {
            properties {
                property("sonar.projectKey", project.findProperty("sonar.project.key") ?: "")
                property("sonar.organization", project.findProperty("sonar.organization") ?: "")
                property("sonar.host.url", "https://sonarcloud.io")
                property("sonar.login", project.findProperty("sonar.login") ?: "")
                property("sonar.android.lint.reportPaths", "build/reports/lint-results-debug.xml")
                property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
            }
        }

        project.tasks.named("check") {
            dependsOn("sonarqube")
        }
    }
}
