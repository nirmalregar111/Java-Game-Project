import java.util.Random;
import java.util.Scanner;

public class Game {

    private Player player;
    private Random random;

    public Game() {
        random = new Random();
    }

    public void start(String playerName, Scanner sc) {

        boolean playAgain = true;
        while (playAgain) {
            player = new Player(playerName);
            runGame(sc);
            endGame();

            System.out.print("\nPlay again? (yes/no): ");
            String again = sc.nextLine().trim().toLowerCase();
            playAgain = again.equals("yes") || again.equals("y");
        }
    }

    private void runGame(Scanner sc) {

        while (!player.hasKey() && player.getHealth() > 0) {

            if (player.getLevel() == 3) {

                Room boss = new BossRoom();

                while (!player.hasKey() && player.getHealth() > 0) {
                    boss.enterRoom(player, sc);
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

            room.enterRoom(player, sc);
        }
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