package game.rooms;

import game.model.Player;
import game.model.Room;
import java.util.Random;

public class TreasureRoom extends Room {

    private Random random = new Random();
    private String treasureType;
    private int scoreReward;

    public TreasureRoom() {
        String[] treasures = {"Ancient Gold Coins", "Ruby Necklace", "Enchanted Scroll", "Diamond Shard", "Dragon Scale"};
        treasureType = treasures[random.nextInt(treasures.length)];
        scoreReward = 20 + random.nextInt(30);
    }

    @Override
    public String getRoomName() { return "\uD83D\uDC8E TREASURE ROOM"; }

    @Override
    public String getRoomDescription() {
        return "A glimmering treasure chest sits in the center of the room.\nYou sense it may contain: " + treasureType
                + "\nBut it could also be trapped...";
    }

    @Override
    public String[] getActions() {
        return new String[]{"Open Chest", "Leave It"};
    }

    @Override
    public String performAction(Player player, int actionIndex) {
        completed = true;
        if (actionIndex == 0) {
            if (random.nextInt(5) == 0) {
                int trapDamage = 10 + random.nextInt(10);
                player.reduceHealth(trapDamage);
                player.addScore(scoreReward / 2);
                return "\u26A0\uFE0F IT WAS A TRAP! You took " + trapDamage + " damage!\nBut you still grabbed "
                        + treasureType + " worth " + (scoreReward / 2) + " score.";
            }
            player.addScore(scoreReward);
            player.addPotion();
            if (!player.hasKey() && random.nextInt(5) == 0) {
                player.setKey(true);
                return "\uD83D\uDCB0 You collected " + treasureType + "! +" + scoreReward + " score"
                        + "\n\uD83E\uDDEA Found a health potion!"
                        + "\n\uD83D\uDDDD\uFE0F YOU FOUND THE HIDDEN KEY inside the chest!!!";
            }
            return "\uD83D\uDCB0 You collected " + treasureType + "! +" + scoreReward + " score"
                    + "\n\uD83E\uDDEA Found a health potion!";
        } else {
            player.addScore(5);
            return "\uD83D\uDEB6 You carefully left the room. +5 score for caution.";
        }
    }
}
