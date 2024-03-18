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

  // Ktor
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
    "netty-jvm",
    "config-yaml",
    "cors"
  ).forEach {
    implementation("io.ktor:ktor-server-$it")
  }

  // Serialization
  listOf(
    "serialization-kotlinx-protobuf",
    "serialization-kotlinx-json"
  ).forEach {
    implementation("io.ktor:ktor-$it:$ktor_version")
  }

  // Ktor Client
  implementation("io.ktor:ktor-client-core-jvm")
  implementation("io.ktor:ktor-client-apache-jvm")
  implementation("io.ktor:ktor-server-openapi")

  // Exposed ORM
  listOf("exposed-core", "exposed-dao", "exposed-jdbc", "exposed-java-time").forEach {
    implementation("org.jetbrains.exposed:exposed-$it:$exposed_version")
  }
  // DI
  implementation(platform("io.insert-koin:koin-bom:3.5.3"))
  implementation("io.insert-koin:koin-core")

  // Database driver
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