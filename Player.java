public class Player {

    private String name;
    private int health;
    private boolean hasKey;
    private int score;
    private int level;
    
    //constructor
    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.hasKey = false;
        this.score = 0;
        this.level = 1;

    }

    // Health reduce method
    public void reduceHealth(int amount) {
    health -= amount;
    if (health < 0) {
        health = 0;
    }
}
    // Key set method
    public void setKey(boolean value) {
        hasKey = value;
    }

    // Getter methods
    public boolean hasKey() {
        return hasKey;
    }

    public int getHealth() {
        return health;
    }

    public void addScore(int points) {
    score += points;

    if (score >= 50) {
        level = 2;
    }
    if (score >= 100) {
        level = 3;
    }
}

public int getScore() {
    return score;
}

public int getLevel() {
    return level;
}

    public void showStatus() {
        System.out.println("Player: " + name);
        System.out.println("Health: " + health);
        System.out.println("Has Key: " + hasKey);
        System.out.println("Level:" + level);
        System.out.println("Score:" + score);
    }
}