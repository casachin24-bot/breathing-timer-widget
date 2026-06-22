# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# signingConfigs.release.enableV1Signing property in build.gradle.kts

-keep class com.breathingtimer.widget.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}
