public class Player {

    private String name;
    private int health;
    private boolean hasKey;
    private int score;
    private int level;
    private int potions;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.hasKey = false;
        this.score = 0;
        this.level = 1;
        this.potions = 0;
    }

    public void reduceHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public void setKey(boolean value) {
        hasKey = value;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public void addScore(int points) {
        score += points;

        if (score >= 100) {
            level = 3;
        } else if (score >= 50) {
            level = 2;
        }
    }

    public void addPotion() {
        potions++;
    }

    public void usePotion() {
        if (potions > 0) {
            health += 30;
            if (health > 100) {
                health = 100;
            }
            potions--;
            System.out.println("🧪 Potion used! +30 Health");
        } else {
            System.out.println("❌ No potions available!");
        }
    }

    public void showStatus() {
        System.out.println("Player: " + name);
        System.out.println("Health: " + health);
        System.out.println("Score: " + score);
        System.out.println("Level: " + level);
        System.out.println("Potions: " + potions);
        System.out.println("Has Key: " + hasKey);
    }
}