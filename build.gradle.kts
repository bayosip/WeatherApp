// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
//        classpath 'com.google.gms:google-services:4.4.1'
//
//        classpath 'com.google.firebase:firebase-crashlytics-gradle:2.9.9'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id ("org.jetbrains.kotlin.plugin.compose").version("2.0.10")
}