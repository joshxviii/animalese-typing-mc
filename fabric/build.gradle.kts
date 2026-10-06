plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

configurations {
    create("common") {
        isCanBeResolved = true
        isCanBeConsumed = false
    }

    compileClasspath.get().extendsFrom(getByName("common"))
    runtimeClasspath.get().extendsFrom(getByName("common"))
    getByName("developmentFabric").extendsFrom(getByName("common"))

    // Files in this configuration will be bundled into your mod using the Shadow plugin.
    // Don't use the `shadow` configuration from the plugin itself as it's meant for excluding files.
    create("shadowBundle") {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

dependencies {
    implementation("net.fabricmc:fabric-loader:${rootProject.property("fabric_loader_version")}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    implementation("net.fabricmc.fabric-api:fabric-api:${rootProject.property("fabric_api_version")}")

    "common"(project(path = ":common")) {
        isTransitive = false
    }

    "shadowBundle"(project(path = ":common", configuration = "transformProductionFabric"))

    // Fabric Kotlin
    implementation("net.fabricmc:fabric-language-kotlin:${rootProject.property("fabric_kotlin_version")}")
    implementation("dev.architectury:architectury-fabric:${rootProject.property("architectury_version")}")
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand(mapOf("version" to inputs.properties["version"]))
    }
}

// Minecraft is not obfuscated on this version, so there is no remapJar task.
// The shaded jar is the mod jar that ships.
tasks.jar {
    archiveClassifier = "dev"
}

tasks.shadowJar {
    configurations = listOf(project.configurations.getByName("shadowBundle"))
    archiveClassifier = null
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

configurations {
    named("apiElements") {
        outgoing.artifacts.clear()
        outgoing.artifact(tasks.shadowJar)
    }

    named("runtimeElements") {
        outgoing.artifacts.clear()
        outgoing.artifact(tasks.shadowJar)
    }
}