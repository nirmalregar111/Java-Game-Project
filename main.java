
import java.util.Scanner;

public class Main {

    public static String playerName;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        playerName = sc.nextLine();
        System.out.println("Enter Your name:");

        System.out.println("\n================================");
        System.out.println("WELCOME " + playerName.toUpperCase());
        System.out.println("TO THE HIDDEN KEY GAME");
        System.out.println("================================\n");

        Game game = new Game();
        game.start(playerName);

    }
}
