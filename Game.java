import java.util.Random;
import java.util.Scanner;

public class Game {

    private Player player;
    private Random random;
    private Scanner scanner;

    public Game() {
        random = new Random();
        scanner = new Scanner(System.in);
    }

    public void start(String playerName) {

        player = new Player(playerName);

        while (!player.hasKey() && player.getHealth() > 0) {

            if (player.getLevel() == 3) {

                Room boss = new BossRoom(scanner);

                while (!player.hasKey() && player.getHealth() > 0) {
                    boss.enterRoom(player);
                }

                break;
            }

            Room room;

            if (random.nextInt(2) == 0) {
                room = new DangerRoom(scanner);
            }
            else {
                room = new TreasureRoom(scanner);
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