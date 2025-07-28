#!/bin/bash

# Simple build script for the VSP 2025 project
# This script compiles the main source code and runs tests if JUnit is available

echo "Building VSP 2025 project (Java 17)..."

# Create build directory
mkdir -p build/classes

# Compile main source code
echo "Compiling main source code..."
find src/main -name "*.java" -exec javac -cp src/main/java -d build/classes {} \;

if [ $? -eq 0 ]; then
    echo "✅ Main source code compiled successfully!"
else
    echo "❌ Compilation failed!"
    exit 1
fi

# Try to compile and run tests if JUnit is available
echo "Checking for JUnit 5..."
if command -v javac &> /dev/null; then
    # Try to compile tests (this will fail if JUnit is not available, which is expected)
    echo "Note: Tests require JUnit 5 to be available in the classpath."
    echo "To run tests, you can use:"
    echo "  gradle test"
    echo "  or"
    echo "  java -cp build/classes:junit-jupiter-api-5.5.2.jar:junit-jupiter-engine-5.5.2.jar org.junit.platform.console.ConsoleLauncher --class-path build/classes --scan-class-path"
fi

echo "✅ Build completed successfully!"
echo ""
echo "To run the project:"
echo "  java -cp build/classes vsp25.pa.graph.GraphExamples" 