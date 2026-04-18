package game;

import game.audio.SoundManager;
import game.ui.GameUI;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SoundManager.init();
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception e) {}
            new GameUI();
        });
    }
}
