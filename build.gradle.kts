import org.gradle.kotlin.dsl.*
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin.*
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.BIN
import org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT
import org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT5
import org.springframework.cloud.contract.verifier.plugin.ContractVerifierExtension

val springCloudVersion by extra { "Hoxton.SR1" }

buildscript {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/snapshot")
        maven("https://repo.spring.io/milestone")
    }
}

plugins {
    java
    id("org.springframework.boot") version "2.2.4.RELEASE"
    id("org.springframework.cloud.contract") version "2.2.1.RELEASE"
}

apply {
    plugin("io.spring.dependency-management")
}

group = "com.spring.reproducer"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}

the<DependencyManagementExtension>().apply {
    imports {
        mavenBom(BOM_COORDINATES)
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.rest-assured:spring-web-test-client")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test") { exclude(module = "junit") }
    testImplementation("org.springframework.cloud:spring-cloud-contract-gradle-plugin")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.withType<Wrapper>().configureEach {
    gradleVersion = "6.2"
    distributionType = BIN
}

configure<ContractVerifierExtension> {
    setTestFramework(JUNIT5)
    // tests also ignored with Junit4:
    //setTestFramework(JUNIT)
    setIgnoredFiles(listOf("contractToIgnore.groovy"))
}
