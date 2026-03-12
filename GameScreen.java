import java.awt.*;
import javax.swing.*;

public class GameScreen extends JFrame {

    private JTextArea gameLog;
    private JLabel healthLabel;
    private JLabel levelLabel;
    private JLabel keyLabel;
    private JLabel scoreLabel;

    private JButton searchBtn;
    private JButton nextBtn;

    private GameEngine engine;

    public GameScreen(String playerName) {

        engine = new GameEngine(playerName);

        setTitle("THE HIDDEN KEY");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,4));

        healthLabel = new JLabel();
        levelLabel = new JLabel();
        keyLabel = new JLabel();
        scoreLabel = new JLabel();

        topPanel.add(healthLabel);
        topPanel.add(levelLabel);
        topPanel.add(keyLabel);
        topPanel.add(scoreLabel);

        add(topPanel,BorderLayout.NORTH);

        gameLog = new JTextArea();
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Consolas",Font.BOLD,18));
        gameLog.setBackground(Color.BLACK);
        gameLog.setForeground(Color.GREEN);

        add(new JScrollPane(gameLog),BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        searchBtn = new JButton("Search Room");
        nextBtn = new JButton("Next Room");

        buttonPanel.add(searchBtn);
        buttonPanel.add(nextBtn);

        add(buttonPanel,BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> {
            String result = engine.searchRoom();
            gameLog.append(result + "\n");
            updateStats();
            checkGameOver();
        });

        nextBtn.addActionListener(e -> {
            String result = engine.nextRoom();
            gameLog.append(result + "\n");
            updateStats();
            checkGameOver();
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

    }

    private void checkGameOver() {

        Player p = engine.getPlayer();

        if (p.getHealth() <= 0) {
            gameLog.append("\n💀 GAME OVER! You have been defeated.\n");
            endGame();
        } else if (engine.isGameWon()) {
            gameLog.append("\n🏆 CONGRATULATIONS! YOU ESCAPED THE DUNGEON!\n");
            endGame();
        }

    }

    private void endGame() {
        searchBtn.setEnabled(false);
        nextBtn.setEnabled(false);
    }
}