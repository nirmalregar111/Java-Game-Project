package game.engine;

import game.model.*;
import game.rooms.*;
import java.util.Random;

public class GameEngine {

    private Player player;
    private Random random;
    private Room currentRoom;
    private int roomNumber;
    private int totalRooms;
    private boolean gameOver;
    private boolean gameWon;

    public GameEngine(String name) {
        player = new Player(name);
        random = new Random();
        roomNumber = 0;
        totalRooms = 5;
        gameOver = false;
        gameWon = false;
        generateNextRoom();
    }

    public void generateNextRoom() {
        roomNumber++;
        if (roomNumber >= totalRooms) {
            currentRoom = new BossRoom();
            return;
        }
        int roll = random.nextInt(10);
        if (roll < 4) currentRoom = new DangerRoom();
        else if (roll < 7) currentRoom = new TreasureRoom();
        else currentRoom = new PuzzleRoom();
    }

    public String performAction(int actionIndex) {
        if (currentRoom == null || gameOver || gameWon) return "Game has ended.";
        String result = currentRoom.performAction(player, actionIndex);
        if (!player.isAlive()) gameOver = true;
        if (player.hasKey() && currentRoom instanceof BossRoom && currentRoom.isCompleted()) gameWon = true;
        return result;
    }

    public String moveToNextRoom() {
        if (gameOver || gameWon) return "Game has ended.";
        if (currentRoom != null && !currentRoom.isCompleted()) return "\u26A0\uFE0F Complete the current room first!";
        if (roomNumber >= totalRooms && currentRoom instanceof BossRoom) {
            if (player.hasKey()) { gameWon = true; return "\uD83C\uDFC6 YOU ESCAPED THE DUNGEON! VICTORY!"; }
        }
        generateNextRoom();
        return "\u27A1\uFE0F Moved to Room " + roomNumber + " of " + totalRooms + "\n" + currentRoom.getRoomName();
    }

    public Room getCurrentRoom() { return currentRoom; }
    public Player getPlayer() { return player; }
    public int getRoomNumber() { return roomNumber; }
    public int getTotalRooms() { return totalRooms; }
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }
    public String usePotion() { return player.heal(30); }
}
