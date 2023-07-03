plugins {
    kotlin("jvm") version "1.5.10"
}

group = "com.infinum.academy"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}