import java.util.Scanner;

public class TreasureRoom extends Room {

    @Override
    public void enterRoom(Player player) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n💎 You found a treasure room!");
        System.out.println("1. Open treasure chest");
        System.out.println("2. Leave it");

        int choice = 0;

        try {
            System.out.print("Choose option: ");
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("⚠ Invalid input!");
            return;
        }

        if (choice == 1) {
            System.out.println("💰 Treasure collected! +50 score.");
            player.addScore(50);
            player.addPotion();
            System.out.println("🧪 You found a health potion!");
        } else {
            System.out.println("Nothing happened.");
        }

        player.showStatus();
    }
}