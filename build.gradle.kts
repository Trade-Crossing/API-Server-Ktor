val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
plugins {
  kotlin("jvm") version "1.9.23"
  id("io.ktor.plugin") version "2.3.8"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
  id("com.google.devtools.ksp") version "1.9.23-1.0.19"
}

group = "com.tradecrossing"
version = "0.0.1"
tasks.jar {
  archiveFileName.set("tradecrossing.jar")
}

// how to change th jar name?

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
    "swagger",
    "content-negotiation-jvm",
    "netty-jvm",
    "config-yaml",
    "cors",
    "openapi",
    "request-validation",
    "call-logging"
  ).forEach {
    implementation("io.ktor:ktor-server-$it:$ktor_version")
  }

  // Serialization
  implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
  listOf(
    "serialization-kotlinx-protobuf",
    "serialization-kotlinx-json"
  ).forEach {
    implementation("io.ktor:ktor-$it:$ktor_version")
  }

  // Ktor Client
  implementation("io.ktor:ktor-client-core-jvm")
  implementation("io.ktor:ktor-client-cio-jvm")
  implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

  // Exposed ORM
  listOf(
    "exposed-core",
    "exposed-dao",
    "exposed-jdbc",
    "exposed-java-time",
  ).forEach {
    implementation("org.jetbrains.exposed:$it:$exposed_version")
  }
  // DI
  // Koin for Ktor
  implementation("io.insert-koin:koin-ktor:3.5.3")
  runtimeOnly("io.insert-koin:koin-core:3.5.3")

  // SLF4J Logger
  implementation("io.insert-koin:koin-logger-slf4j:3.5.3")
  runtimeOnly("io.insert-koin:koin-annotations:1.3.1")

  // Database driver
  implementation("org.postgresql:postgresql:42.7.3")

  // logging
  implementation("ch.qos.logback:logback-classic:$logback_version")

  // Swagger
  implementation("io.github.smiley4:ktor-swagger-ui:2.7.5")
  implementation("io.swagger.core.v3:swagger-annotations:2.2.20")


  // test
  testImplementation("io.ktor:ktor-server-tests-jvm")
  testImplementation("io.ktor:ktor-server-test-host-jvm:2.3.8")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// auto reload
application {
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

//