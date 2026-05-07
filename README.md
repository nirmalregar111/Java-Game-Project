# 🗝️ The Hidden Key — Java Dungeon Adventure Game

A Java-based dungeon crawler where you explore dangerous rooms, collect treasure, fight monsters, and ultimately face a final boss to claim the hidden key and escape!

---

## 📖 Table of Contents

- [Game Overview](#game-overview)
- [Gameplay Mechanics](#gameplay-mechanics)
- [Getting Started](#getting-started)
- [Project Architecture](#project-architecture)
- [Class Descriptions](#class-descriptions)
- [Known Issues & Improvements](#known-issues--improvements)

---

## 🎮 Game Overview

**The Hidden Key** is a text-based dungeon adventure game with an optional graphical menu. As a player, you will:

- Explore randomly generated rooms in search of a hidden key
- Battle monsters and manage your health
- Collect treasure and health potions
- Progress through 3 difficulty levels
- Face a powerful final boss to win the game

**Win Condition:** Defeat the final boss to obtain the hidden key and escape the dungeon.  
**Lose Condition:** Your health reaches 0.

---

## ⚙️ Gameplay Mechanics

### Levels & Scoring

| Level | Score Required | Monster Damage |
|-------|---------------|----------------|
| 1     | 0+            | 20 HP          |
| 2     | 50+           | 30 HP          |
| 3     | 100+          | Boss Fight     |

### Room Types

| Room          | Description                                                   |
|---------------|---------------------------------------------------------------|
| 🏴 Danger Room | Fight a monster (lose HP) or attempt to escape               |
| 💰 Treasure Room | Find treasure (+50 score), collect a health potion, or leave |
| 👑 Boss Room   | Final battle — attack the 150 HP boss to claim the key       |

### Player Stats

- **Health:** Starts at 100 HP. Drops to 0 = game over.
- **Score:** Increases by collecting treasure. Determines your level.
- **Potions:** Restore 30 HP each. Collected in Treasure Rooms.
- **Level:** Increases at score milestones (50 = level 2, 100 = level 3).
- **Key:** Obtained only by defeating the final boss.

### Boss Battle

The final boss has **150 HP**. Each round:
- You deal **40 damage** to the boss.
- The boss deals **35 damage** to you if it survives.
- Use potions to heal during the fight.
- Minimum **4 attack rounds** needed to defeat the boss.

---

## 🚀 Getting Started

### Prerequisites

- **Java 8 or higher** — [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- Terminal / Command Prompt

### Build

```bash
# Navigate to the project directory
cd Java-Game-Project

# Compile all source files
javac *.java
```

### Run — CLI Mode (Text-based)

```bash
java Main
```

You will be prompted to enter your player name, then the game begins in the terminal.

### Run — GUI Mode (Graphical Menu)

```bash
java GameUI
```

Launches an animated graphical menu. Requires `background.jpg` and `icon.png` in the project directory.

---

## 🏗️ Project Architecture

```
Java-Game-Project/
├── Main.java          # Entry point — reads player name, starts CLI game
├── Game.java          # Game loop — manages room progression and win/loss
├── GameEngine.java    # Alternate game logic for the GUI version
├── Player.java        # Player state — health, score, level, potions, key
├── Room.java          # Abstract base class for all room types
├── DangerRoom.java    # Combat room — fight or flee
├── TreasureRoom.java  # Reward room — treasure and potions
├── BossRoom.java      # Final boss encounter
├── GameUI.java        # Animated graphical main menu (Swing)
├── GameScreen.java    # In-game stats/actions UI window (Swing)
├── background.jpg     # Background image for GUI
└── icon.png           # Application icon
```

### Class Hierarchy

```
Room (abstract)
├── DangerRoom
├── TreasureRoom
└── BossRoom
```

---

## 📋 Class Descriptions

### `Main.java`
Entry point for the CLI version. Prompts the user for a player name and launches the `Game`.

### `Game.java`
Controls the main game loop for the CLI version. Randomly selects between `DangerRoom` and `TreasureRoom` until the player reaches level 3, then enters the `BossRoom`. Handles win/loss outcomes.

### `GameEngine.java`
Alternative game engine used by the GUI (`GameScreen`). Implements similar room-search logic with random room selection and level advancement.

### `Player.java`
Manages all player state including:
- Health (with `reduceHealth()` clamped at 0)
- Score (with automatic level advancement at 50 and 100 points)
- Potions (collected and used for +30 HP recovery)
- Key (set to `true` when the boss is defeated)

### `Room.java`
Abstract base class with a single abstract method `enterRoom(Player player)` that each room subclass implements.

### `DangerRoom.java`
Presents the player with a combat choice:
1. **Fight** — takes damage (20 HP at level 1, 30 HP at level 2, 50 HP at level 3) but earns +10 score
2. **Escape** — runs away safely

### `TreasureRoom.java`
Presents the player with three choices:
1. **Search for treasure** — earn +50 score
2. **Grab a health potion** — add 1 potion to inventory
3. **Leave** — exit safely with no reward

### `BossRoom.java`
The final encounter. The boss starts with 150 HP. Each turn:
1. **Attack** — deal 40 damage to the boss; if boss survives it retaliates for 35 HP
2. **Use Potion** — restore 30 HP

Defeating the boss awards the key and +100 score.

### `GameUI.java`
An animated Swing-based main menu featuring particle effects, fog clouds, glowing buttons, and a title label. Provides entry points to start a new game or quit.

### `GameScreen.java`
A minimal Swing window showing the player's current stats (health, score, level, potions) with action buttons that delegate to `GameEngine`.

---

## 🐛 Known Issues & Improvements

### Known Issues

1. **BossRoom health resets on re-entry** — Each call to `new BossRoom()` resets `bossHealth` to 150. The `Game.java` loop correctly reuses the same `BossRoom` instance, but `GameEngine.java` may recreate it.

2. **Scanner resource leaks** — Each room creates its own `Scanner(System.in)` instance. These are never closed, which can cause resource leak warnings.

3. **No input validation loop** — If a user enters a non-numeric choice, the current catch block applies a penalty hit rather than re-prompting.

4. **Inconsistent dual-mode architecture** — The project has two separate game flows: a CLI version (`Main` → `Game`) and a GUI version (`GameUI` → `GameScreen` → `GameEngine`). Game logic is duplicated between `Game.java` and `GameEngine.java`.

### Suggested Improvements

- [ ] **Add JUnit tests** — Unit tests for `Player` state transitions, damage calculations, and room outcome logic
- [ ] **Add Maven or Gradle build** — Simplify compilation and dependency management with a `pom.xml` or `build.gradle`
- [ ] **Consolidate game logic** — Merge `Game.java` and `GameEngine.java` into a single engine used by both CLI and GUI frontends
- [ ] **Extract constants** — Move hardcoded values (damage amounts, score thresholds, health limits) into a `GameConstants` class
- [ ] **Add difficulty settings** — Let the player choose Easy / Normal / Hard before starting
- [ ] **Save/load game state** — Persist player progress to a file using Java serialization or JSON
- [ ] **Expand room variety** — Add more room types (trap rooms, merchant rooms, puzzle rooms)
- [ ] **Full GUI for room interactions** — Replace text-based room prompts with Swing dialogs in GUI mode

---

## 📄 License

This project is open-source and available for educational and personal use.
