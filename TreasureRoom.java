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
            System.out.println("⚠ Invalid input! Try again next time.");
            return;
        }

        if (choice == 1) {
            System.out.println("🎉 You found the hidden key!");
            player.setKey(true);
        } else {
            System.out.println("Nothing happened.");
        }

        player.showStatus();
    }
}