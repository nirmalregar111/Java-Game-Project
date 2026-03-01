import java.util.Scanner;

public class Room {

    public void enterRoom(Player player) {

        Scanner sc = new Scanner(System.in);

        while (!player.hasKey() && player.getHealth() > 0) {

            System.out.println("\nYou are inside a dark room...");
            System.out.println("1. Open the box");
            System.out.println("2. Check under the table");
            System.out.println("3. Leave the room");

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                System.out.println("🎉 You found the hidden key!");
                player.setKey(true);
            }
            else if (choice == 2) {
                System.out.println("❌ Nothing here! You lost 20 health.");
                player.reduceHealth(20);
            }
            else if (choice == 3) {
                System.out.println("❌ You hit the wall! Lost 10 health.");
                player.reduceHealth(10);
            }
            else {
                System.out.println("⚠ Invalid choice! Try again.");
            }

            player.showStatus();
        }

        if (player.hasKey()) {
            System.out.println("\n🏆 YOU WIN THE GAME!");
        } else {
            System.out.println("\n💀 GAME OVER!");
        }
    }
}