public class Player {

    String name;
    int health;
    boolean hasKey;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.hasKey = false;
    }

    public void showStatus() {
        System.out.println("Player: " + name);
        System.out.println("Health: " + health);
        System.out.println("Has Key: " + hasKey);
    }
}