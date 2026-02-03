plugins {
    id("java-library")
    id("org.allaymc.gradle.plugin") version "0.2.1"
}

group = "com.allaymc.mobarena"
description = "A PvE arena system where players fight waves of mobs and earn rewards"
version = "0.1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allay {
    api = "0.24.0"

    plugin {
        entrance = ".MobArena"
        authors += "OpenClaw"
        website = "https://github.com/daoge-cmd/MobArena"
    }
}

dependencies {
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.34")
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.34")
}
