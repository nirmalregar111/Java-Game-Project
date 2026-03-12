import java.awt.*;
import javax.swing.*;

public class GameScreen extends JFrame {

    private JTextArea gameLog;
    private JLabel healthLabel;
    private JLabel levelLabel;
    private JLabel keyLabel;
    private JLabel scoreLabel;
    private JLabel potionLabel;

    private GameEngine engine;

    public GameScreen(String playerName) {

        engine = new GameEngine(playerName);

        setTitle("THE HIDDEN KEY");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,3));

        healthLabel = new JLabel();
        levelLabel = new JLabel();
        keyLabel = new JLabel();
        scoreLabel = new JLabel();
        potionLabel = new JLabel();

        topPanel.add(healthLabel);
        topPanel.add(levelLabel);
        topPanel.add(keyLabel);
        topPanel.add(scoreLabel);
        topPanel.add(potionLabel);

        add(topPanel,BorderLayout.NORTH);

        gameLog = new JTextArea();
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Consolas",Font.BOLD,18));
        gameLog.setBackground(Color.BLACK);
        gameLog.setForeground(Color.GREEN);

        add(new JScrollPane(gameLog),BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton searchBtn = new JButton("Search Room");
        JButton nextBtn = new JButton("Next Room");
        JButton potionBtn = new JButton("Use Potion");

        buttonPanel.add(searchBtn);
        buttonPanel.add(nextBtn);
        buttonPanel.add(potionBtn);

        add(buttonPanel,BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> {
            gameLog.append(engine.searchRoom() + "\n");
            updateStats();
        });

        nextBtn.addActionListener(e -> {
            gameLog.append(engine.nextRoom() + "\n");
            updateStats();
        });

        potionBtn.addActionListener(e -> {
            Player p = engine.getPlayer();
            if (p.getPotions() > 0) {
                int healthBefore = p.getHealth();
                p.usePotion();
                int healed = p.getHealth() - healthBefore;
                gameLog.append("🧪 Potion used! +" + healed + " Health\n");
            } else {
                gameLog.append("❌ No potions available!\n");
            }
            updateStats();
        });

        updateStats();

        setVisible(true);
    }

    private void updateStats() {

        Player p = engine.getPlayer();

        healthLabel.setText("❤️ Health: " + p.getHealth());
        levelLabel.setText("🏰 Level: " + p.getLevel());
        keyLabel.setText("🗝 Key: " + (p.hasKey() ? "YES" : "NO"));
        scoreLabel.setText("⭐ Score: " + p.getScore());
        potionLabel.setText("🧪 Potions: " + p.getPotions());

    }
}