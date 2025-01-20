plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jacoco:org.jacoco.core:0.8.12")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
}

gradlePlugin {
    plugins {
        create("jacocoConventionPlugin") {
            id = "com.uhufor.jacoco-convention"
            implementationClass = "com.uhufor.JacocoConventionPlugin"
        }
        create("sonarCloudConventionPlugin") {
            id = "com.uhufor.sonarcloud-convention"
            implementationClass = "com.uhufor.SonarCloudConventionPlugin"
        }
    }
}
