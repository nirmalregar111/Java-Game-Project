# The Hidden Key — Java Dungeon Game

## Problem Statement

Design and implement a **text-based dungeon adventure game** in Java that challenges players to navigate dangerous rooms, collect resources, level up, and ultimately defeat a final boss to escape with a hidden key. The game must demonstrate core object-oriented programming concepts including inheritance, encapsulation, and polymorphism, while providing an engaging, interactive gameplay experience through the console.

## Game Overview

**The Hidden Key** is a dungeon-escape adventure game where the player explores a series of randomly generated rooms, fights monsters, collects treasure, and battles a final boss to win.

### Objective

Find the **hidden key** by defeating the final boss and escaping the dungeon before your health reaches zero.

## How to Play

### Running the Game

**Prerequisites:** Java 11 or higher

**Compile:**
```bash
javac *.java
```

**Run (Console version):**
```bash
java Main
```

**Run (GUI version):**
```bash
java GameUI
```

### Gameplay

1. Enter your player name when prompted.
2. You will be placed in a randomly generated dungeon room each turn.
3. Manage your health, score, and potions as you progress.
4. Reach **Level 3** (score ≥ 100) to unlock the **Boss Room**.
5. Defeat the boss to obtain the key and win the game.

### Room Types

| Room | Description | Reward / Risk |
|------|-------------|---------------|
| 🔥 Danger Room | Fight a monster or run away | +20 score / -30–50 HP |
| 💎 Treasure Room | Open a chest or leave it | +50 score + 1 potion |
| 👑 Boss Room | Fight the final boss (unlocked at Level 3) | +100 score, obtain key |

### Player Stats

| Stat | Starting Value | Description |
|------|---------------|-------------|
| Health | 100 | Decreases from monster/boss attacks; game over at 0 |
| Score | 0 | Increases by fighting and collecting treasure |
| Level | 1 | Increases as score grows (Level 2 at 50+, Level 3 at 100+) |
| Potions | 0 | Found in treasure rooms; restore 30 HP each |

### Controls (Console)

- Type `1` to select the first option in any room.
- Type `2` to select the second option in any room.

## Project Structure

```
Java-Game-Project/
├── Main.java          — Entry point: prompts for name and starts the game
├── Game.java          — Core game loop and win/loss logic
├── Player.java        — Player stats, inventory, and leveling
├── Room.java          — Base class for all room types
├── DangerRoom.java    — Combat encounter room
├── TreasureRoom.java  — Reward room with treasure and potions
├── BossRoom.java      — Final boss battle room
├── GameUI.java        — Animated GUI main menu (Swing)
├── GameScreen.java    — GUI gameplay screen (Swing)
├── GameEngine.java    — Game engine used by the GUI version
└── background.jpg     — Background image for the GUI
```

## Design Highlights

- **Object-Oriented Design**: Room hierarchy using inheritance (`Room` → `DangerRoom`, `TreasureRoom`, `BossRoom`)
- **Encapsulation**: Player state managed through private fields and public methods
- **Polymorphism**: Each room type overrides `enterRoom(Player)` for unique behaviour
- **Dual Interface**: Supports both a console-based and a Swing GUI interface
