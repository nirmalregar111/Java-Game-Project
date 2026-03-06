import java.util.Random;

public class Game {

    private Player player;
    private Random random;

    public Game() {
        random = new Random();
    }

    public void start(String playerName) {

        player = new Player(playerName);

        while (!player.hasKey() && player.getHealth() > 0) {

            if (player.getLevel() == 3) {

                Room boss = new BossRoom();

                while (!player.hasKey() && player.getHealth() > 0) {
                    boss.enterRoom(player);
                }

                break;
            }

            Room room;

            if (random.nextInt(2) == 0) {
                room = new DangerRoom();
            }
            else {
                room = new TreasureRoom();
            }

            room.enterRoom(player);
        }

        endGame();
    }

    private void endGame() {

        if (player.hasKey()) {
            System.out.println("\n🏆 CONGRATULATIONS! YOU WIN!");
        }
        else {
            System.out.println("\n💀 GAME OVER!");
        }

    }
}