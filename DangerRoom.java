import java.util.Scanner;

public class DangerRoom extends Room {

    @Override
    public void enterRoom(Player player) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n🔥 You entered a dangerous room!");
        System.out.println("1. Fight monster");
        System.out.println("2. Run away");

        System.out.print("Choose option: ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice == 1) {
            System.out.println("You got hurt! Lost 30 health.");
            player.reduceHealth(30);
        } else {
            System.out.println("You escaped safely.");
        }

        player.showStatus();
    }
}