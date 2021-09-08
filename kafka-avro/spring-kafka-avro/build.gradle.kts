import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"

	id("com.github.davidmc24.gradle.plugin.avro") version "1.2.1"
}

group = "br.com.devcave.avro"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
	mavenCentral()
	maven {
		url = uri("https://packages.confluent.io/maven")
	}
}

val springCloudVersion = "2020.0.3"
val avroVersion = "1.10.2"
val avroSerializerVersion = "6.2.0"
val swaggerVersion = "3.0.0"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.apache.avro:avro:$avroVersion")
	implementation("io.confluent:kafka-avro-serializer:$avroSerializerVersion")

	implementation("org.springframework.kafka:spring-kafka")

	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")

	// Swagger
	implementation("io.springfox:springfox-boot-starter:$swaggerVersion")
	implementation("io.springfox:springfox-swagger-ui:$swaggerVersion")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "16"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask> {
	setSource("src/main/resources/avro")
	setOutputDir(file("src/main/avro"))
}

avro {
	isCreateSetters.set(false)
	fieldVisibility.set("PRIVATE")
	outputCharacterEncoding.set("UTF-8")
	stringType.set("String")
	templateDirectory.set(null as String?)
	isEnableDecimalLogicalType.set(true)
}