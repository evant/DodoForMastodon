enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}

rootProject.name="DodoForMastodon"

include(":di")
include(":app-android")
include(":app-desktop")
//include(":app-iOS")

include(":ui:timeline")
//include(":ui:messages")
//include(":ui:notifications")
//include(":ui:search")
//include(":ui:settings")
include(":ui:common")
include(":ui:root")
include(":ui:signed-in")
include(":ui:signed-out")
include(":ui:desktop-webview")

include(":domain:timeline")
include(":domain:authentication")

//include(":data-timeline")
include(":data:persistence")
include(":data:network")
include(":data:repository")
