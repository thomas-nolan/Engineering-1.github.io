# University Maze Escape — README

## Project Overview

### Game Title
Escape from York

### Short Description
Learn about university life by having to escape from a maze set at the university. With only 5 minutes you need to collect the key card to unlock doors, avoid being caught by the dean and avoid setting of any hidden events.  

### Key Features
- Maze navigation and exploration  
- Hidden event  
- 5-minute timer with pause function  
- Speed boosting potion
- Dynamic scoring system
- Enemy dean 

---

## Gameplay Summary

### Goal
Gather a key card to unlock the escape route

### Challenges
Locked doors, enemy dean and a hidden event which you may trigger

### Rewards
Bonus points for completing the game quickly 

### Hidden Events
A tripwire feature which will lock one of the doors in the maze, triggered by the play 

### Timer
A timer which counts down from 5 minutes located on top of the screen 

### Scoring System
Points are determined at the end of the game and are based on the speed which you can finish the maze

---

## Game Structure & Design

### Map Description
The map is designed to mimic a university, it contains study areas, a reception and lots of green spaces including an office  

### Player Movement / Controls
- **Movement:** W/A/S/D or arrow keys
- **Pause/unpause Game:** Esc key

### Difficulty
At the moment the game is only based on one difficulty that being the base level although there are future plans to increase it as well as allow for different modes in future

### Development Choices
The design of the map was based around the licensing of online assets. In addition we chose to use libGDX as it allowed for full cross compatibily with all platforms.

---

## Technical Details

### Engine / Framework
libGDX

### Programming Language
Java  

### Dependencies / Libraries
- [libGDX](https://libgdx.com/) - The main framework for the game
- [Tiled](https://thorbjorn.itch.io/tiled) - .tmx Map editor
- [Modern Interiors Tileset](https://limezu.itch.io/moderninteriors/devlog/244045/free-version-overview-18042021-update) - Tile set used for interiors and walls
- [RPG Tileset](https://pipoya.itch.io/pipoya-rpg-tileset-32x32) - Tile set used for exteriors

### Project Structure
- core/ – Contains all main game code and logic.
- assets/ – Holds images, sounds, and other game resources.
- lwjgl3/ – Desktop launcher module (runs the game using LWJGL3).
- docs/ – Project documentation.
- gradle & config files – For building and project settings.

## How to run
Double click on the JAR file or enter the following command in the terminal to run:

Windows and Linux:
java -jar Team_5_Game.jar

## Implementation for Assessment 1

### Events Implemented
- **Negative Event:** Some doors around the map will be locked which will result in you needing to collect a key card to unlock them
- **Positive Event:** Collecting a coffee cup which will cause you to move faster  
- **Hidden Event:** When you enter a room you will set of a tripwire which will lock a door

### Timer
- 5-minute timer implemented and functional  
- Option to pause the timer when needed

### Event Counter
- Displays the number of interactions or events that have been triggered

---
