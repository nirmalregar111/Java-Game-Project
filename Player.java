public class Player {

    private String name;
    private int health;
    private boolean hasKey;
    
    //constructor
    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.hasKey = false;
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

    public void showStatus() {
        System.out.println("Player: " + name);
        System.out.println("Health: " + health);
        System.out.println("Has Key: " + hasKey);
    }
}