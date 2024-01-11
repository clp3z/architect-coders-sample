import com.devexperto.architectcoders.buildsrc.Libs

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Libs.Kotlin.Coroutines.core)
    implementation(Libs.JavaX.inject)
}
