package game.rooms;

import game.model.Player;
import game.model.Room;
import java.util.Random;

public class DangerRoom extends Room {

    private Random random = new Random();
    private String monsterName;
    private int monsterDamage;

    public DangerRoom() {
        String[] monsters = {"Skeleton Warrior", "Shadow Wraith", "Cave Troll", "Dark Spider", "Cursed Knight"};
        monsterName = monsters[random.nextInt(monsters.length)];
        monsterDamage = 15 + random.nextInt(20);
    }

    public String getMonsterName() { return monsterName; }

    @Override
    public String getRoomName() { return "\uD83D\uDD25 DANGER ROOM"; }

    @Override
    public String getRoomDescription() {
        return "A " + monsterName + " blocks your path!\nIt snarls and prepares to attack.\nDamage threat: " + monsterDamage + " HP";
    }

    @Override
    public String[] getActions() {
        return new String[]{"Fight Monster", "Run Away"};
    }

    @Override
    public String performAction(Player player, int actionIndex) {
        completed = true;
        if (actionIndex == 0) {
            player.reduceHealth(monsterDamage);
            int scoreGain = 15 + random.nextInt(15);
            player.addScore(scoreGain);
            if (!player.isAlive()) {
                return "\u2694\uFE0F You fought the " + monsterName + " but took " + monsterDamage
                        + " damage!\n\uD83D\uDC80 You have fallen...";
            }
            if (random.nextInt(4) == 0) {
                player.addPotion();
                return "\u2694\uFE0F You defeated the " + monsterName + "! Took " + monsterDamage
                        + " damage, gained " + scoreGain + " score.\n\uD83E\uDDEA You found a health potion!";
            }
            return "\u2694\uFE0F You defeated the " + monsterName + "! Took " + monsterDamage
                    + " damage, gained " + scoreGain + " score.";
        } else {
            int fleeDamage = 5 + random.nextInt(8);
            player.reduceHealth(fleeDamage);
            return "\uD83C\uDFC3 You ran away from the " + monsterName + "! Took " + fleeDamage + " damage while escaping.";
        }
    }
}
