import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to The Hidden Key Game!");
        System.out.print("Enter your name: ");

        String name = sc.nextLine();

        Player player = new Player(name);   
        player.showStatus();

        Room room1 = new Room();
        room1.enterRoom(player);

    }
}