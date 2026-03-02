import java.util.Random;
import java.util.Scanner;

public class Game {

    private Player player;
    private Scanner sc;
    private Random random;

    public Game() {
        sc = new Scanner(System.in);
        random = new Random();
    }

    public void start() {

        System.out.println("Welcome to The Hidden Key Game!");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        player = new Player(name);

        while (!player.hasKey() && player.getHealth() > 0) {

            Room room;

            if (random.nextInt(2) == 0) {
                room = new DangerRoom();
            } else {
                room = new TreasureRoom();
            }

            room.enterRoom(player);
        }

        endGame();
    }

    private void endGame() {
        if (player.hasKey()) {
            System.out.println("\n🏆 CONGRATULATIONS! YOU WIN!");
        } else {
            System.out.println("\n💀 GAME OVER!");
        }
    }
}