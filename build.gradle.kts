import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false

    val kotlinVer = "1.6.20"
    kotlin("jvm") version kotlinVer apply false
    kotlin("plugin.spring") version kotlinVer apply false
    kotlin("plugin.jpa") version kotlinVer apply false
}

group = "io.github.sgpublic"
version = "1.0-SNAPSHOT"

subprojects {
    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

apply(from = "assemble.gradle.kts")