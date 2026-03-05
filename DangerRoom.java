import java.util.Scanner;

public class DangerRoom extends Room {

    @Override
    public void enterRoom(Player player) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n🔥 You entered a dangerous room!");
        System.out.println("1. Fight monster");
        System.out.println("2. Run away");

        int choice = 0;

        try {
            System.out.print("Choose option: ");
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("⚠ Invalid input! Monster attacked!");
            player.reduceHealth(10);
            player.showStatus();
            return;
        }

        if (choice == 1) {

            int damage;

            if (player.getLevel() == 1) {
                damage = 30;
            } else if (player.getLevel() == 2) {
                damage = 40;
            } else {
                damage = 50;
            }

            System.out.println("Monster attacked! Lost " + damage + " health.");
            player.reduceHealth(damage);
            player.addScore(20);

        } else {
            System.out.println("You escaped safely.");
        }

        player.showStatus();
    }
}