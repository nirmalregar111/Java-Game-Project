import java.util.Scanner;

public class BossRoom extends Room {

    private int bossHealth = 150;

    @Override
    public void enterRoom(Player player, Scanner sc) {

        System.out.println("\n👑 FINAL BOSS ROOM!");
        System.out.println("Boss Health: " + bossHealth);

        System.out.println("1. Attack Boss");
        System.out.println("2. Use Potion");

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

            int playerDamage = 40;

            bossHealth -= playerDamage;

            System.out.println("⚔️ You attacked the boss! -" + playerDamage + " HP");

            if (bossHealth <= 0) {

                System.out.println("🔥 Boss defeated!");
                player.setKey(true);
                player.addScore(100);

            } else {

                int bossDamage = 35;

                System.out.println("💥 Boss attacked! -" + bossDamage + " HP");

                player.reduceHealth(bossDamage);
            }

        } 
        else if (choice == 2) {

            player.usePotion();

        } 
        else {

            System.out.println("Invalid action!");
        }

        if (bossHealth > 0) {
            System.out.println("Boss Health: " + bossHealth);
        }
        player.showStatus();
    }
}