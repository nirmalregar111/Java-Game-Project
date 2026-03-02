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
            System.out.println("⚠ Invalid input! You got confused!");
            player.reduceHealth(10);
            return;
        }

        if (choice == 1) {
            System.out.println("You got hurt! Lost 30 health.");
            player.reduceHealth(30);
        } else {
            System.out.println("You escaped safely.");
        }

        player.showStatus();
    }
}