import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val bouncycastle_version: String by project
val commons_codec_version: String by project
val jbcrypt_version: String by project
val ktor_version: String by project
val kmongo_version: String by project
val kotlin_version: String by project
val multibase_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.20"
    id("io.ktor.plugin") version "2.3.0"
    kotlin("plugin.serialization") version "1.8.20"
}

group = "social.aceinpink"
version = "0.0.1"
application {
    mainClass.set("social.aceinpink.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":java-multicodec"))

    implementation("commons-codec:commons-codec:$commons_codec_version")
    implementation("io.ktor:ktor-network-tls-certificates:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-default-headers:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-cbor:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation("org.bouncycastle:bcprov-jdk15on:$bouncycastle_version")
    implementation("org.mindrot:jbcrypt:$jbcrypt_version")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}