val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val exposed_version: String by project
plugins {
  kotlin("jvm") version "1.9.22"
  id("io.ktor.plugin") version "2.3.8"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

group = "com.tradecrossing"
version = "0.0.1"

application {
  mainClass.set("io.ktor.server.netty.EngineMain")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

dependencies {

  listOf(
    "core-jvm",
    "auth-jvm",
    "auth-jwt-jvm",
    "host-common-jvm",
    "resources",
    "status-pages-jvm",
    "swagger-jvm",
    "content-negotiation-jvm",
    "serialization-kotlinx-json-jvm",
    "server-netty-jvm",
    "server-config-yaml"
  ).forEach {
    implementation("io.ktor:ktor-server-$it")
  }

  listOf(
    "serialization-kotlinx-protobuf",
    "serialization-kotlinx-json-jvm"
  ).forEach {
    implementation("io.ktor:ktor-$it:$ktor_version")
  }

  // Ktor Client
  implementation("io.ktor:ktor-client-core-jvm")
  implementation("io.ktor:ktor-client-apache-jvm")
  implementation("io.ktor:ktor-server-openapi")

  // database
  implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

  listOf("exposed-core", "exposed-dao", "exposed-jdbc", "exposed-java-time").forEach {
    implementation("org.jetbrains.exposed:exposed-$it:$exposed_version")
  }

  implementation("org.postgresql:postgresql:42.2")

  // logging
  implementation("ch.qos.logback:logback-classic:$logback_version")

  // test
  testImplementation("io.ktor:ktor-server-tests-jvm")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// auto reload
application {
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}