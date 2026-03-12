import java.awt.*;
import javax.swing.*;

public class GameScreen extends JFrame {

    private JTextArea gameLog;
    private JLabel healthLabel;
    private JLabel levelLabel;
    private JLabel keyLabel;

    private GameEngine engine;
    private JButton searchBtn;
    private JButton nextBtn;

    public GameScreen(String playerName) {

        engine = new GameEngine(playerName);

        setTitle("THE HIDDEN KEY");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,3));

        healthLabel = new JLabel();
        levelLabel = new JLabel();
        keyLabel = new JLabel();

        topPanel.add(healthLabel);
        topPanel.add(levelLabel);
        topPanel.add(keyLabel);

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
            gameLog.append(engine.searchRoom() + "\n");
            updateStats();
            checkGameEnd();
        });

        nextBtn.addActionListener(e -> {
            gameLog.append(engine.nextRoom() + "\n");
            updateStats();
            checkGameEnd();
        });

        updateStats();

        setVisible(true);
    }

    private void updateStats() {

        Player p = engine.getPlayer();

        healthLabel.setText("❤️ Health: " + p.getHealth());
        levelLabel.setText("🏰 Level: " + p.getLevel());
        keyLabel.setText("🗝 Key: " + (p.hasKey() ? "YES" : "NO"));

    }

    private void checkGameEnd() {
        if (engine.isGameOver()) {
            searchBtn.setEnabled(false);
            nextBtn.setEnabled(false);
            gameLog.setForeground(Color.RED);
            gameLog.append("\n💀 GAME OVER! Better luck next time.\n");
        } else if (engine.isWon()) {
            searchBtn.setEnabled(false);
            nextBtn.setEnabled(false);
            gameLog.setForeground(new Color(255, 215, 0));
            gameLog.append("\n🏆 CONGRATULATIONS! YOU ESCAPED THE DUNGEON!\n");
        }
    }
}