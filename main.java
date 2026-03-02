import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to The Hidden Key Game!");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        Player player = new Player(name);
        player.showStatus();

        Room danger = new DangerRoom();     // Polymorphism
        Room treasure = new TreasureRoom(); // Polymorphism

        danger.enterRoom(player);

        if (player.getHealth() > 0) {
            treasure.enterRoom(player);
        }

        if (player.hasKey()) {
            System.out.println("\n🏆 YOU WIN THE GAME!");
        } else {
            System.out.println("\n💀 GAME OVER!");
        }
    }
}