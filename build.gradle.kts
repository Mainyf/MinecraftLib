@file:Suppress("UNCHECKED_CAST")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `kotlin-dsl`
    `maven-publish`
    kotlin("jvm") version "1.3.11"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
}


ext {
    set("group", "io.github.mainyf")
    set("mainClass", "io.github.mainyf.minecraftlib.MinecraftLib")
    set("version", "1.0")
    set("javaVersion", "1.8")
    set("server", "./server")
    set("Authors", listOf("Mainyf"))
    set("mavenRepoPath", "")
}

group = ext["group"]!! as String
version = ext["version"]!! as String

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
}

dependencies {
    compile(files("server/craftbukkit1.7.10.jar"))
    archives(kotlin("stdlib-jdk8"))
    archives("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.6")
    archives("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.6")
    archives("org.apache.commons:commons-lang3:+")
//    archives("org.kodein.di:kodein-di-generic-jvm:6.1.0")
    testCompile("junit", "junit", "4.12")
    configurations.compile.get().extendsFrom(configurations.archives.get())
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.toVersion(project.ext["javaVersion"]!!)
    targetCompatibility = JavaVersion.toVersion(project.ext["javaVersion"]!!)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = project.ext["javaVersion"]!! as String
}

val allJar by tasks.registering(Jar::class) {
    archiveClassifier.set("all")
    dependsOn("classes")
    from(
        sourceSets.main.get().output.classesDirs.files,
        sourceSets.main.get().output.resourcesDir,
        configurations.getByName("archives").map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks["javadoc"])
}

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            artifactId = project.name.toLowerCase()

            artifact(sourceJar.get())
            artifact(javadocJar.get())
        }
    }
    repositories {
        maven(url = project.ext["mavenRepoPath"]!!)
    }
}

bukkit {
    name = project.name
    main = ext["mainClass"]!! as String
    version = project.version as String
    authors = ext["Authors"]!! as List<String>
}

// copy jar to server plugins path
val copyToPluginFolder by tasks.registering(Copy::class) {
    from(allJar)
    into("${ext["server"]}/plugins/")
}