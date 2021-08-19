plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.21"
    id("org.jetbrains.kotlin.kapt") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "2.0.3"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.21"
    id("org.zeroturnaround.gradle.jrebel") version "1.1.10"
}

version = "0.1"
group = "com.example"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

micronaut {
    runtime("netty")
    testRuntime("kotest")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-tracing")
    implementation("io.micronaut.aws:micronaut-aws-parameter-store")
    implementation("io.micronaut.aws:micronaut-aws-secretsmanager")
    implementation("io.micronaut.discovery:micronaut-discovery-client")
    implementation("io.micronaut.kafka:micronaut-kafka")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.micrometer:micronaut-micrometer-core")
    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-datadog")
    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus")
    implementation("io.micronaut.problem:micronaut-problem-json")
    implementation("io.micronaut.rabbitmq:micronaut-rabbitmq")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.xml:micronaut-jackson-xml")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("io.jaegertracing:jaeger-thrift")
    runtimeOnly("mysql:mysql-connector-java")
    runtimeOnly("org.apache.logging.log4j:log4j-api:2.14.1")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:testcontainers")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

}


application {
    mainClass.set("com.example.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("1.8")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
run.dependsOn(generateRebel)
run {
    if (project.hasProperty("rebelAgent")) {
        jvmArgs(rebelAgent)
    }
}


}
