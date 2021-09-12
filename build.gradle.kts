plugins {
    java
    id("io.izzel.taboolib") version "1.26"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

taboolib {
    install("common")
    install("common-5")
    install("module-chat")
    install("module-configuration")
    install("module-database")
    install("module-lang")
    install("module-metrics")
    install("module-nms")
    install("module-nms-util")
    install("module-ui")
    install("module-ui-receptacle")
    install("platform-bukkit")
    description {
        contributors {
            name("冰上云梦")
        }
        dependencies {
            name("PlayerPoints")
            name("Vault")
            name("PlaceholderAPI")
        }
        links {
            name("homepage").url("https://bingzi.online")
        }
    }
    classifier = null
    version = "6.0.0-53"
}

repositories {
    mavenCentral()
    maven("https://repo.rosewooddev.io/repository/public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("ink.ptms.core:v11701:11701:mapped")
    compileOnly("ink.ptms.core:v11701:11701:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
    compileOnly("org.black_ixx:playerpoints:3.0.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
