#!/bin/bash

# Build APK for Breathing Timer Widget
echo "Building APK..."

# Clean previous builds
./gradlew clean

# Build release APK
./gradlew assembleRelease

# Check if build successful
if [ -f "app/build/outputs/apk/release/app-release-unsigned.apk" ]; then
    echo "✅ APK built successfully!"
    echo "Location: app/build/outputs/apk/release/app-release-unsigned.apk"
else
    echo "❌ Build failed"
    exit 1
fi
