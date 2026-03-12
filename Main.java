
public class Main {

    public static String playerName;

    public static void main(String[] args) {

        System.out.println("Enter Your name:");
        playerName = Room.sc.nextLine();

        System.out.println("\n================================");
        System.out.println("WELCOME " + playerName.toUpperCase());
        System.out.println("TO THE HIDDEN KEY GAME");
        System.out.println("================================\n");

        Game game = new Game();
        game.start(playerName);

    }
}
