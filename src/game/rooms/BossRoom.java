package game.rooms;

import game.model.Player;
import game.model.Room;
import java.util.Random;

public class BossRoom extends Room {

    private int bossHealth = 150;
    private int bossMaxHealth = 150;
    private String bossName;
    private Random random = new Random();

    public BossRoom() {
        String[] bossNames = {"The Dungeon Lord", "Shadow King", "Dragon of the Abyss"};
        bossName = bossNames[random.nextInt(bossNames.length)];
    }

    public int getBossHealth() { return bossHealth; }
    public int getBossMaxHealth() { return bossMaxHealth; }
    public String getBossName() { return bossName; }

    @Override
    public String getRoomName() { return "\uD83D\uDC51 BOSS ROOM \u2014 " + bossName; }

    @Override
    public String getRoomDescription() {
        return "\u26A0\uFE0F " + bossName + " stands before you!\n"
                + "Boss HP: " + bossHealth + "/" + bossMaxHealth + "\n"
                + "This is the final battle. Defeat the boss to claim the KEY and escape!";
    }

    @Override
    public String[] getActions() {
        if (bossHealth <= 0) return new String[]{"Claim Victory"};
        return new String[]{"Attack Boss", "Use Potion", "Dodge"};
    }

    @Override
    public String performAction(Player player, int actionIndex) {
        if (bossHealth <= 0) {
            completed = true;
            player.setKey(true);
            player.addScore(100);
            return "\uD83C\uDFC6 You claimed the HIDDEN KEY from " + bossName + "'s remains!\n+100 score! You can now ESCAPE THE DUNGEON!";
        }

        StringBuilder result = new StringBuilder();

        if (actionIndex == 0) {
            int playerDamage = 30 + random.nextInt(25);
            boolean critical = random.nextInt(5) == 0;
            if (critical) { playerDamage *= 2; result.append("\u2728 CRITICAL HIT! "); }
            bossHealth -= playerDamage;
            result.append("\u2694\uFE0F You attacked " + bossName + " for " + playerDamage + " damage!");
            if (bossHealth <= 0) {
                bossHealth = 0;
                result.append("\n\uD83D\uDD25 " + bossName + " HAS BEEN DEFEATED!");
                result.append("\nBoss HP: 0/" + bossMaxHealth);
                return result.toString();
            }
            int bossDamage = 20 + random.nextInt(20);
            player.reduceHealth(bossDamage);
            result.append("\n\uD83D\uDCA5 " + bossName + " strikes back for " + bossDamage + " damage!");
            result.append("\nBoss HP: " + bossHealth + "/" + bossMaxHealth);

        } else if (actionIndex == 1) {
            result.append(player.heal(30));
            int bossDamage = 15 + random.nextInt(10);
            player.reduceHealth(bossDamage);
            result.append("\n\uD83D\uDCA5 " + bossName + " attacks while you heal! -" + bossDamage + " HP");
            result.append("\nBoss HP: " + bossHealth + "/" + bossMaxHealth);

        } else {
            if (random.nextBoolean()) {
                result.append("\uD83D\uDCA8 You dodged " + bossName + "'s attack!");
                int counterDamage = 15 + random.nextInt(10);
                bossHealth -= counterDamage;
                result.append("\n\u2694\uFE0F Quick counter-attack for " + counterDamage + " damage!");
                if (bossHealth <= 0) {
                    bossHealth = 0;
                    result.append("\n\uD83D\uDD25 " + bossName + " HAS BEEN DEFEATED!");
                }
                result.append("\nBoss HP: " + Math.max(bossHealth, 0) + "/" + bossMaxHealth);
            } else {
                int bossDamage = 25 + random.nextInt(15);
                player.reduceHealth(bossDamage);
                result.append("\u274c Dodge failed! " + bossName + " hits you for " + bossDamage + " damage!");
                result.append("\nBoss HP: " + bossHealth + "/" + bossMaxHealth);
            }
        }
        return result.toString();
    }
}
