# Iskat's Day Out

A simple Java platformer game made for CMSC 22 (Object-Oriented Programming).

## Features
- Play as Iskat, just a cat 
- Attack enemies, navigate platforms, and score points
- Simple animations and enemy logic

## Concepts Used
- Java OOP (Inheritance, Polymorphism, Encapsulation)
- Game loop and collision detection
- Sprite animations

## How to Run (Gradle)

### Requirements
- Java 21 or later

### Setup & Run
1. **Clone the repository**
2. **Run the game**
   ```bash
   ./gradlew run        # macOS/Linux
   gradlew.bat run      # Windows
   ```
   JavaFX dependencies are downloaded automatically via the Gradle plugin.

### Build JAR
```bash
./gradlew build
```
The JAR will be in `build/libs/`.

### Create a standalone installer (.exe)
```bash
./gradlew jpackage
```
The installer will be generated in `build/jpackage/`.



<!--
TODO:
   - Install WiX later to build installer package 
   - Check the installer works on Windows
   - 
-->