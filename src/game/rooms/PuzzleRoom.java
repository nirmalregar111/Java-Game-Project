package game.rooms;

import game.model.Player;
import game.model.Room;
import java.util.Random;

public class PuzzleRoom extends Room {

    private Random random = new Random();
    private String riddle;
    private int correctAnswer;
    private String[] options;
    private boolean answered = false;

    private static final String[][] PUZZLES = {
        {"I have keys but no locks. I have space but no room. You can enter but can't go inside. What am I?",
         "A Keyboard", "A Map", "A House"},
        {"The more you take, the more you leave behind. What am I?",
         "Footsteps", "Money", "Time"},
        {"I speak without a mouth and hear without ears. I have no body, but I come alive with the wind. What am I?",
         "An Echo", "A Ghost", "A Shadow"},
        {"What has a heart that doesn't beat?",
         "An Artichoke", "A Robot", "A Zombie"},
        {"I can fly without wings. I can cry without eyes. Wherever I go, darkness follows. What am I?",
         "A Cloud", "A Bat", "Smoke"},
        {"What gets wetter the more it dries?",
         "A Towel", "The Sun", "Sand"},
        {"I am not alive, but I grow. I don't have lungs, but I need air. What am I?",
         "Fire", "A Plant", "A Crystal"},
        {"What can travel around the world while staying in a corner?",
         "A Stamp", "A Spider", "A Shadow"}
    };

    public PuzzleRoom() {
        int idx = random.nextInt(PUZZLES.length);
        riddle = PUZZLES[idx][0];
        options = new String[]{PUZZLES[idx][1], PUZZLES[idx][2], PUZZLES[idx][3]};
        correctAnswer = random.nextInt(3);
        if (correctAnswer != 0) {
            String temp = options[0];
            options[0] = options[correctAnswer];
            options[correctAnswer] = temp;
        }
    }

    @Override
    public String getRoomName() { return "\uD83E\uDDE9 PUZZLE ROOM"; }

    @Override
    public String getRoomDescription() {
        return "A mysterious inscription glows on the wall...\n\n\u2753 \"" + riddle + "\"\n\nChoose your answer wisely!";
    }

    @Override
    public String[] getActions() {
        if (answered) return new String[]{"Continue"};
        return options.clone();
    }

    @Override
    public String performAction(Player player, int actionIndex) {
        if (answered) { completed = true; return "\u27A1\uFE0F Moving on..."; }
        answered = true;
        completed = true;

        if (actionIndex == correctAnswer) {
            int scoreGain = 25 + random.nextInt(15);
            player.addScore(scoreGain);
            if (!player.hasKey() && random.nextInt(4) == 0) {
                player.setKey(true);
                return "\u2705 CORRECT! The inscription reveals a secret compartment!\n+" + scoreGain
                        + " score\n\uD83D\uDDDD\uFE0F YOU FOUND THE HIDDEN KEY!";
            }
            return "\u2705 CORRECT! +" + scoreGain + " score!\nThe answer was: " + options[correctAnswer];
        } else {
            int damage = 15 + random.nextInt(10);
            player.reduceHealth(damage);
            return "\u274c WRONG! The curse strikes you for " + damage + " damage!\nThe correct answer was: " + options[correctAnswer];
        }
    }
}
