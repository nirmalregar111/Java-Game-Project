package game.model;

public class Player {

    private String name;
    private int health;
    private int maxHealth;
    private boolean hasKey;
    private int score;
    private int level;
    private int potions;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.maxHealth = 100;
        this.hasKey = false;
        this.score = 0;
        this.level = 1;
        this.potions = 1;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public int getPotions() { return potions; }
    public boolean hasKey() { return hasKey; }
    public boolean isAlive() { return health > 0; }

    public void setKey(boolean value) { hasKey = value; }

    public void reduceHealth(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public String heal(int amount) {
        if (potions > 0) {
            potions--;
            health += amount;
            if (health > maxHealth) health = maxHealth;
            return "\u2728 Potion used! +" + amount + " Health restored. Potions left: " + potions;
        }
        return "\u274c No potions available!";
    }

    public void addScore(int points) {
        score += points;
        if (score >= 100) level = 3;
        else if (score >= 50) level = 2;
    }

    public void addPotion() { potions++; }
}
