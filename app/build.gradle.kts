plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    kotlin("plugin.jpa") version "2.0.20"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "projects"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot core
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // PostgreSQL JDBC
    runtimeOnly("org.postgresql:postgresql")

    // WebClient (Spring WebFlux client for async HTTP)
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // Jackson Kotlin module (JSON mapping)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Kotlin core libs
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

}

kotlin {
    jvmToolchain(21)
}


tasks.test {
    useJUnitPlatform()
}
