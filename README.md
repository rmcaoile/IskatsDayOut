# Iskat's Day Out 🐱🎮

A simple Java platformer game made for CMSC 22 (Object-Oriented Programming).

## 🚀 Features
- Play as Iskat, just a cat 
- Attack enemies, navigate platforms, and score points
- Simple animations and enemy logic

## 🧠 Concepts Used
- Java OOP (Inheritance, Polymorphism, Encapsulation)
- Game loop and collision detection
- Sprite animations

## 🖥️ How to Run

### ✅ Requirements
- Java 11 or later
- Eclipse IDE
- [JavaFX SDK](https://gluonhq.com/products/javafx/)

### 🛠️ Setup Instructions (Eclipse)
1. **Download and Extract JavaFX SDK**  
   - Get it from: [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)  
   - Extract it to a known folder (e.g., `C:\javafx-sdk-21`)

2. **Import the Project into Eclipse**  
   - Open Eclipse  
   - Go to `File > Import > General > Existing Projects into Workspace`  
   - Select the folder where the project was cloned

3. **Add JavaFX to Build Path**  
   - Right-click the project > `Build Path > Configure Build Path`  
   - Go to the `Libraries` tab  
   - Click `Add External JARs...` and select all JARs inside the `lib` folder of your JavaFX SDK

4. **Set VM Arguments to Enable JavaFX Modules**  
   - Right-click the project > `Run As > Run Configurations...`  
   - Select your `Main` class under Java Application  
   - Go to the `Arguments` tab  
   - In **VM arguments**, add:

     ```
     --module-path "C:\path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
     ```

     ⚠️ Replace `C:\path\to\javafx-sdk\lib` with your actual path.

5. **Run the Game**  
   - Go to `src\main`  
   - Right-click `MainClass.java`  
   - Select `Run As > Java Application`

