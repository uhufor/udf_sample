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
}

gradlePlugin {
    plugins {
        create("jacocoConventionPlugin") {
            id = "com.uhufor.jacoco-convention"
            implementationClass = "com.uhufor.JacocoConventionPlugin"
        }
    }
}
