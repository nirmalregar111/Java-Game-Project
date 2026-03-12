import java.util.Random;

public class GameEngine {

    private Player player;
    private Random random;
    private boolean keyFound = false;
    private int room = 1;

    public GameEngine(String name) {
        player = new Player(name);
        random = new Random();
    }

    public String searchRoom() {

        if (isGameOver()) {
            return "💀 Game over! You have no health remaining.";
        }

        if (isWon()) {
            return "🏆 You have already escaped the dungeon!";
        }

        if (keyFound) {
            return "You already found the key.";
        }

        if (random.nextInt(3) == 0) {

            keyFound = true;
            player.setKey(true);

            return "🗝 You found the HIDDEN KEY!";
        }

        if (random.nextBoolean()) {

            int damage = random.nextInt(10) + 5;

            player.reduceHealth(damage);

            if (player.getHealth() <= 0) {
                return "👹 Enemy attacked! You lost " + damage + " health. 💀 You have been defeated!";
            }

            return "👹 Enemy attacked! You lost " + damage + " health.";
        }

        player.addScore(10);

        return "Nothing found... but you gained 10 score.";
    }

    public String nextRoom() {

        if (isGameOver()) {
            return "💀 Game over! You have no health remaining.";
        }

        if (isWon()) {
            return "🏆 You have already escaped the dungeon!";
        }

        if (room < 4) {
            room++;
        }

        if (room == 3 && player.hasKey()) {

            return "👑 Boss Room reached! Prepare for final fight!";
        }

        if (room >= 4 && player.hasKey()) {

            return "🏆 YOU ESCAPED THE DUNGEON! YOU WIN!";
        }

        return "➡ You moved to room " + room + (room >= 4 ? ". Keep searching for the key!" : "");
    }

    public boolean isGameOver() {
        return player.getHealth() <= 0;
    }

    public boolean isWon() {
        return player.hasKey() && room >= 4;
    }

    public Player getPlayer() {
        return player;
    }

}