
import java.util.Scanner;

public class Main {

    public static String playerName;

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter Your name: ");
            playerName = sc.nextLine().trim();
            while (playerName.isEmpty()) {
                System.out.print("Name cannot be empty. Enter Your name: ");
                playerName = sc.nextLine().trim();
            }

            System.out.println("\n================================");
            System.out.println("WELCOME " + playerName.toUpperCase());
            System.out.println("TO THE HIDDEN KEY GAME");
            System.out.println("================================\n");

            Game game = new Game();
            game.start(playerName, sc);
        }

    }
}
