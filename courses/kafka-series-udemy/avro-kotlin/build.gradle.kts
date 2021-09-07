plugins {
    kotlin("jvm") version "1.5.10"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.2.1"
}

group = "br.com.devcave"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.avro:avro:1.10.2")
    implementation("io.confluent:kafka-avro-serializer:6.2.0")
    implementation("org.apache.kafka:kafka-clients:2.8.0")
}

tasks.withType<com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask> {
    setSource("src/main/resources/avro")
}

avro {
    isCreateSetters.set(false)
    fieldVisibility.set("PRIVATE")
    outputCharacterEncoding.set("UTF-8")
    stringType.set("String")
    templateDirectory.set(null as String?)
    isEnableDecimalLogicalType.set(true)
}