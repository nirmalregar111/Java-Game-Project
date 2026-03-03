import java.util.Scanner;

public class BossRoom extends Room {

    @Override
    public void enterRoom(Player player) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n👑 FINAL BOSS ROOM!");
        System.out.println("1. Fight Final Boss");
        System.out.println("2. Use Potion");
        System.out.println("3. Try to Escape");

        int choice = 0;

        try {
            System.out.print("Choose option: ");
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("⚠ You hesitated! Boss attacked!");
            player.reduceHealth(30);
            player.showStatus();
            return;
        }

        if (choice == 1) {

            int bossDamage = 60;

            System.out.println("💥 Boss attacked! Lost " + bossDamage + " health.");
            player.reduceHealth(bossDamage);

            if (player.getHealth() > 0) {
                System.out.println("🔥 You defeated the Final Boss!");
                player.addScore(100);
                player.setKey(true);
            }

        } else if (choice == 2) {
            player.usePotion();
        } else {
            System.out.println("❌ You cannot escape the boss!");
            player.reduceHealth(40);
        }

        player.showStatus();
    }
}