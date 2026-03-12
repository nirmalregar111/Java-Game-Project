import java.util.Scanner;

public class Room {

    // Single shared Scanner for all room interactions; System.in is intentionally
    // left open for the lifetime of the application (closed on JVM exit).
    protected static final Scanner sc = new Scanner(System.in);

    public void enterRoom(Player player) {
        System.out.println("You entered a room.");
    }
}