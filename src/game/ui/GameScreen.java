package game.ui;

import game.audio.SoundManager;
import game.engine.GameEngine;
import game.model.*;
import game.rooms.*;
import game.sprites.SpriteRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GameScreen extends JFrame {

    private GameEngine engine;
    private JTextArea gameLog;
    private JPanel actionPanel;
    private JLabel healthLabel, scoreLabel, levelLabel, keyLabel, potionLabel, roomLabel;
    private JProgressBar healthBar;
    private Timer animationTimer;
    private GameBgPanel bgPanel;
    private SpritePanel spritePanel;
    private float animPhase = 0f;

    private static final Color GOLD = new Color(255, 215, 0);
    private static final Color RED = new Color(220, 50, 50);
    private static final Color GREEN = new Color(50, 220, 80);
    private static final Color CYAN = new Color(80, 200, 255);
    private static final Color TEXT_COLOR = new Color(220, 220, 230);
    private static final Color DIM_TEXT = new Color(150, 150, 170);

    public GameScreen(String playerName) {
        engine = new GameEngine(playerName);
        setTitle("The Hidden Key - " + playerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        bgPanel = new GameBgPanel();
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        buildTopBar();
        buildCenter();
        buildBottomBar();

        animationTimer = new Timer(16, e -> {
            animPhase += 0.04f;
            if (animPhase > (float)(Math.PI * 200)) animPhase -= (float)(Math.PI * 200);
            bgPanel.updateP();
            if (spritePanel != null) spritePanel.repaint();
            bgPanel.repaint();
        });
        animationTimer.start();

        updateAll();
        appendLog("\u2694\uFE0F Welcome, " + playerName + "! Your quest begins...\n");
        appendLog("\u27A1\uFE0F Room 1 of " + engine.getTotalRooms() + "\n");
        appendLog(engine.getCurrentRoom().getRoomName() + "\n");
        appendLog(engine.getCurrentRoom().getRoomDescription() + "\n---\n");
        setVisible(true);
    }

    private void buildTopBar() {
        JPanel top = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(10, 5, 15, 230));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 215, 0, 60));
                g2.fillRect(0, getHeight() - 2, getWidth(), 2);
            }
        };
        top.setOpaque(false);
        top.setPreferredSize(new Dimension(0, 70));
        top.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 18, 5, 18);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font sf = new Font("Serif", Font.BOLD, 17);

        roomLabel = mkLabel("\uD83C\uDFF0 Room 1/5", GOLD, sf);
        gbc.gridx = 0; gbc.weightx = 0.12; top.add(roomLabel, gbc);

        JPanel hp = new JPanel();
        hp.setOpaque(false);
        hp.setLayout(new BoxLayout(hp, BoxLayout.Y_AXIS));
        healthLabel = mkLabel("\u2764\uFE0F Health: 100/100", RED, sf);
        hp.add(healthLabel);
        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(100);
        healthBar.setPreferredSize(new Dimension(180, 12));
        healthBar.setMaximumSize(new Dimension(220, 12));
        healthBar.setBorderPainted(false);
        healthBar.setForeground(GREEN);
        healthBar.setBackground(new Color(40, 20, 20));
        healthBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        hp.add(healthBar);
        gbc.gridx = 1; gbc.weightx = 0.22; top.add(hp, gbc);

        scoreLabel = mkLabel("\uD83C\uDFC6 Score: 0", GOLD, sf);
        gbc.gridx = 2; gbc.weightx = 0.14; top.add(scoreLabel, gbc);
        levelLabel = mkLabel("\u2B50 Level: 1", CYAN, sf);
        gbc.gridx = 3; gbc.weightx = 0.14; top.add(levelLabel, gbc);
        keyLabel = mkLabel("\uD83D\uDD11 Key: NO", DIM_TEXT, sf);
        gbc.gridx = 4; gbc.weightx = 0.14; top.add(keyLabel, gbc);
        potionLabel = mkLabel("\uD83E\uDDEA Potions: 1", GREEN, sf);
        gbc.gridx = 5; gbc.weightx = 0.14; top.add(potionLabel, gbc);

        bgPanel.add(top, BorderLayout.NORTH);
    }

    private JLabel mkLabel(String t, Color c, Font f) {
        JLabel l = new JLabel(t); l.setFont(f); l.setForeground(c); return l;
    }

    private void buildCenter() {
        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));

        // Sprite display area
        spritePanel = new SpritePanel();
        spritePanel.setPreferredSize(new Dimension(0, 300));
        center.add(spritePanel, BorderLayout.NORTH);

        // Game log
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        gameLog.setLineWrap(true);
        gameLog.setWrapStyleWord(true);
        gameLog.setFont(new Font("Consolas", Font.PLAIN, 15));
        gameLog.setBackground(new Color(8, 5, 15));
        gameLog.setForeground(new Color(180, 220, 180));
        gameLog.setCaretColor(GREEN);
        gameLog.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JScrollPane sp = new JScrollPane(gameLog);
        sp.setBorder(BorderFactory.createLineBorder(new Color(60, 50, 30), 2));
        sp.getViewport().setBackground(new Color(8, 5, 15));
        sp.setOpaque(false);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        center.add(sp, BorderLayout.CENTER);

        bgPanel.add(center, BorderLayout.CENTER);
    }

    // ===== SPRITE PANEL - Draws player & enemy side by side =====
    class SpritePanel extends JPanel {
        SpritePanel() { setOpaque(false); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();

            // Dark panel bg with border
            g2.setColor(new Color(8, 5, 15, 180));
            g2.fill(new RoundRectangle2D.Double(0, 0, w, h, 20, 20));
            g2.setColor(new Color(255, 215, 0, 30));
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, 20, 20));

            Player p = engine.getPlayer();
            Room room = engine.getCurrentRoom();

            // Left: Player sprite
            int sprW = 220, sprH = h - 30;
            int playerX = 30, playerY = 15;
            SpriteRenderer.drawPlayer(g2, playerX, playerY, sprW, sprH, animPhase, p.getHealth(), p.getMaxHealth());

            // Player name below
            g2.setFont(new Font("Serif", Font.BOLD, 16));
            g2.setColor(CYAN);
            FontMetrics fm = g2.getFontMetrics();
            String pName = p.getName();
            g2.drawString(pName, playerX + (sprW - fm.stringWidth(pName)) / 2, h - 5);

            // Center: Room name + VS
            g2.setFont(new Font("Serif", Font.BOLD, 22));
            g2.setColor(GOLD);
            String rn = room != null ? room.getRoomName() : "";
            fm = g2.getFontMetrics();
            g2.drawString(rn, (w - fm.stringWidth(rn)) / 2, 30);

            if (room instanceof DangerRoom || room instanceof BossRoom) {
                g2.setFont(new Font("Serif", Font.BOLD, 36));
                g2.setColor(new Color(255, 50, 50, (int)(150 + 100 * Math.sin(animPhase * 3))));
                fm = g2.getFontMetrics();
                g2.drawString("VS", (w - fm.stringWidth("VS")) / 2, h / 2 + 10);
            }

            // Right: Enemy / Room sprite
            int enemyX = w - sprW - 30, enemyY = 15;

            if (room instanceof DangerRoom) {
                String mn = ((DangerRoom) room).getMonsterName();
                SpriteRenderer.drawMonster(g2, enemyX, enemyY, sprW, sprH, mn, animPhase);
                // Monster name
                g2.setFont(new Font("Serif", Font.BOLD, 15));
                g2.setColor(RED);
                fm = g2.getFontMetrics();
                g2.drawString(mn, enemyX + (sprW - fm.stringWidth(mn)) / 2, h - 5);

            } else if (room instanceof BossRoom) {
                BossRoom br = (BossRoom) room;
                SpriteRenderer.drawBoss(g2, enemyX, enemyY, sprW, sprH,
                        br.getBossName(), br.getBossHealth(), br.getBossMaxHealth(), animPhase);
                g2.setFont(new Font("Serif", Font.BOLD, 15));
                g2.setColor(new Color(255, 50, 30));
                fm = g2.getFontMetrics();
                g2.drawString(br.getBossName(), enemyX + (sprW - fm.stringWidth(br.getBossName())) / 2, h - 5);

            } else if (room instanceof TreasureRoom) {
                SpriteRenderer.drawTreasure(g2, enemyX, enemyY, sprW, sprH, room.isCompleted(), animPhase);
                g2.setFont(new Font("Serif", Font.BOLD, 15));
                g2.setColor(GOLD);
                fm = g2.getFontMetrics();
                g2.drawString("Treasure Chest", enemyX + (sprW - fm.stringWidth("Treasure Chest")) / 2, h - 5);

            } else if (room instanceof PuzzleRoom) {
                SpriteRenderer.drawPuzzleRune(g2, enemyX, enemyY, sprW, sprH, animPhase);
                g2.setFont(new Font("Serif", Font.BOLD, 15));
                g2.setColor(new Color(180, 120, 255));
                fm = g2.getFontMetrics();
                g2.drawString("Ancient Rune", enemyX + (sprW - fm.stringWidth("Ancient Rune")) / 2, h - 5);
            }

            g2.dispose();
        }
    }

    private void buildBottomBar() {
        JPanel wrap = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(10, 5, 15, 230));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 215, 0, 40));
                g2.fillRect(0, 0, getWidth(), 2);
            }
        };
        wrap.setOpaque(false);
        wrap.setPreferredSize(new Dimension(0, 90));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 15));
        left.setOpaque(false);
        JButton menuBtn = mkBtn("\u2B05 Menu", new Color(80, 60, 40), new Color(120, 90, 50));
        menuBtn.addActionListener(e -> {
            SoundManager.playClick();
            int c = JOptionPane.showConfirmDialog(this, "Return to Main Menu?\nProgress will be lost.", "Confirm", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) { animationTimer.stop(); dispose(); SwingUtilities.invokeLater(GameUI::new); }
        });
        left.add(menuBtn);
        JButton potBtn = mkBtn("\uD83E\uDDEA Potion", new Color(20, 80, 40), new Color(30, 130, 60));
        potBtn.addActionListener(e -> { SoundManager.playHeal(); appendLog(engine.usePotion() + "\n"); updateAll(); });
        left.add(potBtn);
        wrap.add(left, BorderLayout.WEST);

        actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));
        actionPanel.setOpaque(false);
        wrap.add(actionPanel, BorderLayout.CENTER);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 15));
        right.setOpaque(false);
        JButton nextBtn = mkBtn("\u27A1 Next Room", new Color(40, 40, 80), new Color(60, 60, 140));
        nextBtn.addActionListener(e -> {
            SoundManager.playClick();
            Room cur = engine.getCurrentRoom();
            if (cur != null && !cur.isCompleted()) { appendLog("\u26A0\uFE0F Complete the current room first!\n"); return; }
            if (engine.isGameWon()) { showEndScreen(true); return; }
            String r = engine.moveToNextRoom();
            appendLog(r + "\n");
            if (engine.isGameWon()) { showEndScreen(true); return; }
            Room nr = engine.getCurrentRoom();
            if (nr != null) appendLog(nr.getRoomDescription() + "\n---\n");
            updateAll();
        });
        right.add(nextBtn);
        wrap.add(right, BorderLayout.EAST);

        bgPanel.add(wrap, BorderLayout.SOUTH);
    }

    private JButton mkBtn(String text, Color bg, Color hov) {
        JButton btn = new JButton(text) {
            boolean h = false;
            float gf = 0;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { h = true; setCursor(new Cursor(Cursor.HAND_CURSOR)); }
                public void mouseExited(MouseEvent e) { h = false; setCursor(Cursor.getDefaultCursor()); }
            });
            Timer t = new Timer(16, e -> {
                if (h && gf < 1) { gf = Math.min(1, gf + 0.1f); repaint(); }
                else if (!h && gf > 0) { gf = Math.max(0, gf - 0.06f); repaint(); }
            }); t.start(); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w=getWidth(),ht=getHeight();
                int r=(int)(bg.getRed()+(hov.getRed()-bg.getRed())*gf);
                int gr=(int)(bg.getGreen()+(hov.getGreen()-bg.getGreen())*gf);
                int b=(int)(bg.getBlue()+(hov.getBlue()-bg.getBlue())*gf);
                if(gf>0){g2.setColor(new Color(hov.getRed(),hov.getGreen(),hov.getBlue(),(int)(50*gf)));
                g2.fill(new RoundRectangle2D.Double(-3,-3,w+6,ht+6,20,20));}
                g2.setColor(new Color(r,gr,b,230));g2.fill(new RoundRectangle2D.Double(0,0,w,ht,15,15));
                g2.setColor(new Color(Math.min(255,r+60),Math.min(255,gr+60),Math.min(255,b+30),180));
                g2.setStroke(new BasicStroke(1.5f));g2.draw(new RoundRectangle2D.Double(1,1,w-2,ht-2,15,15));
                FontMetrics fm=g2.getFontMetrics(getFont());int tx=(w-fm.stringWidth(getText()))/2;int ty=(ht-fm.getHeight())/2+fm.getAscent();
                g2.setColor(new Color(0,0,0,120));g2.drawString(getText(),tx+1,ty+1);
                g2.setColor(getForeground());g2.drawString(getText(),tx,ty);
            }
        };
        btn.setFont(new Font("Serif", Font.BOLD, 16));
        btn.setForeground(TEXT_COLOR);
        btn.setPreferredSize(new Dimension(155, 50));
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false); btn.setOpaque(false);
        return btn;
    }

    private void updateAll() {
        Player p = engine.getPlayer();
        healthLabel.setText("\u2764\uFE0F Health: " + p.getHealth() + "/" + p.getMaxHealth());
        healthBar.setValue(p.getHealth());
        healthBar.setForeground(p.getHealth() > 60 ? GREEN : p.getHealth() > 30 ? new Color(220, 180, 30) : RED);
        scoreLabel.setText("\uD83C\uDFC6 Score: " + p.getScore());
        levelLabel.setText("\u2B50 Level: " + p.getLevel());
        potionLabel.setText("\uD83E\uDDEA Potions: " + p.getPotions());
        roomLabel.setText("\uD83C\uDFF0 Room " + engine.getRoomNumber() + "/" + engine.getTotalRooms());
        keyLabel.setText(p.hasKey() ? "\uD83D\uDD11 Key: YES!" : "\uD83D\uDD11 Key: NO");
        keyLabel.setForeground(p.hasKey() ? GOLD : DIM_TEXT);
        updateActions();
        if (engine.isGameOver()) showEndScreen(false);
    }

    private void updateActions() {
        actionPanel.removeAll();
        Room room = engine.getCurrentRoom();
        if (room == null || room.isCompleted()) {
            JLabel dl = new JLabel("\u2705 Room completed! Click 'Next Room' \u27A1");
            dl.setFont(new Font("Serif", Font.BOLD, 18)); dl.setForeground(GOLD);
            actionPanel.add(dl);
        } else {
            String[] acts = room.getActions();
            for (int i = 0; i < acts.length; i++) {
                final int idx = i;
                Color bg, hv;
                String a = acts[i].toLowerCase();
                if (a.contains("fight") || a.contains("attack")) { bg = new Color(100, 20, 20); hv = new Color(180, 40, 40); }
                else if (a.contains("run") || a.contains("dodge") || a.contains("leave")) { bg = new Color(80, 60, 20); hv = new Color(140, 100, 30); }
                else if (a.contains("potion") || a.contains("heal")) { bg = new Color(20, 80, 40); hv = new Color(30, 140, 60); }
                else { bg = new Color(30, 30, 80); hv = new Color(50, 50, 150); }
                JButton btn = mkBtn(acts[i], bg, hv);
                btn.addActionListener(e -> {
                    int oldHealth = engine.getPlayer().getHealth();
                    boolean wasPuzzle = engine.getCurrentRoom() instanceof PuzzleRoom;
                    boolean wasTreasure = engine.getCurrentRoom() instanceof TreasureRoom;
                    if (a.contains("fight") || a.contains("attack")) SoundManager.playFight();
                    else if (a.contains("potion") || a.contains("heal")) SoundManager.playHeal();
                    else SoundManager.playClick();
                    appendLog(engine.performAction(idx) + "\n---\n");
                    if (engine.getPlayer().getHealth() < oldHealth) SoundManager.playDamage();
                    if ((wasPuzzle || wasTreasure) && engine.getCurrentRoom().isCompleted()) SoundManager.playPuzzle();
                    updateAll();
                });
                actionPanel.add(btn);
            }
        }
        actionPanel.revalidate(); actionPanel.repaint();
    }

    private void appendLog(String t) { gameLog.append(t); gameLog.setCaretPosition(gameLog.getDocument().getLength()); }

    private void showEndScreen(boolean won) {
        if (won) SoundManager.playWin(); else SoundManager.playGameOver();
        animationTimer.stop();
        Timer delay = new Timer(300, ev -> {
            JDialog d = new JDialog(this, true); d.setUndecorated(true); d.setSize(getSize()); d.setLocationRelativeTo(this);
            Player p = engine.getPlayer();
            JPanel panel = new JPanel() {
                float alpha = 0;
                Timer fi;
                {
                    fi = new Timer(16, e2 -> { alpha = Math.min(1, alpha + 0.02f); repaint(); if (alpha >= 1) fi.stop(); });
                    fi.start();
                }
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int w = getWidth(), h = getHeight();
                    g2.setPaint(new GradientPaint(w/2f,0, won ? new Color(10,25,10) : new Color(30,5,5),
                            w/2f,h, won ? new Color(20,50,20) : new Color(10,0,0)));
                    g2.fillRect(0,0,w,h);
                    Color glow = won ? new Color(255,215,0,(int)(80*alpha)) : new Color(180,0,0,(int)(60*alpha));
                    g2.setPaint(new RadialGradientPaint(w/2f,h/2.5f,w*0.5f,new float[]{0,1},new Color[]{glow,new Color(0,0,0,0)}));
                    g2.fillRect(0,0,w,h);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2.setFont(new Font("Serif", Font.BOLD, 80));
                    String t = won ? "\uD83C\uDFC6 VICTORY! \uD83C\uDFC6" : "\uD83D\uDC80 GAME OVER \uD83D\uDC80";
                    FontMetrics fm = g2.getFontMetrics(); int tx = (w-fm.stringWidth(t))/2;
                    g2.setColor(new Color(0,0,0,180)); g2.drawString(t,tx+4,h/3+4);
                    g2.setColor(won ? GOLD : RED); g2.drawString(t,tx,h/3);
                    g2.setFont(new Font("Serif", Font.BOLD, 32));
                    String sub = won ? "Congratulations, "+p.getName()+"!" : p.getName()+" has perished...";
                    fm = g2.getFontMetrics(); g2.setColor(TEXT_COLOR); g2.drawString(sub,(w-fm.stringWidth(sub))/2,h/3+70);
                    g2.setFont(new Font("Serif", Font.PLAIN, 24));
                    String[] stats = {"Score: "+p.getScore(),"Level: "+p.getLevel(),"Health: "+p.getHealth()+"/"+p.getMaxHealth(),"Rooms: "+engine.getRoomNumber()};
                    int sy = h/2+20;
                    for (String s : stats) { fm = g2.getFontMetrics(); g2.setColor(new Color(200,210,200)); g2.drawString(s,(w-fm.stringWidth(s))/2,sy); sy += 40; }
                }
            };
            panel.setLayout(new BorderLayout());
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15)); btns.setOpaque(false);
            JButton rb = mkBtn(won ? "\uD83C\uDFE0 Menu" : "\uD83D\uDD04 Retry", new Color(40,60,40), new Color(60,100,60));
            rb.addActionListener(e3 -> { SoundManager.playClick(); d.dispose(); dispose(); SwingUtilities.invokeLater(GameUI::new); });
            JButton eb = mkBtn("\uD83D\uDEAA Exit", new Color(80,20,20), new Color(140,40,40));
            eb.addActionListener(e3 -> { SoundManager.playClick(); System.exit(0); });
            btns.add(rb); btns.add(eb);
            panel.add(btns, BorderLayout.SOUTH);
            d.setContentPane(panel); d.setVisible(true);
        }); delay.setRepeats(false); delay.start();
    }

    // Background panel with particles
    static class GameBgPanel extends JPanel {
        private java.util.List<float[]> ps = new ArrayList<>();
        private Random r = new Random();
        GameBgPanel() { setDoubleBuffered(true); for (int i=0;i<50;i++) ps.add(new float[]{r.nextInt(1920),r.nextInt(1080),-0.3f-r.nextFloat()*1.2f,2+r.nextInt(3),0.1f+r.nextFloat()*0.3f,30+r.nextInt(30),60+r.nextInt(70),130+r.nextInt(90)}); }
        void updateP() { for (float[] p : ps) { p[1]+=p[2]; if(p[1]<-15){p[1]=getHeight()+15;p[0]=r.nextInt(Math.max(getWidth(),1));} } }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g); int w=getWidth(),h=getHeight(); Graphics2D g2=(Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(w/2f,0,new Color(10,8,20),w/2f,h,new Color(20,10,15))); g2.fillRect(0,0,w,h);
            g2.setPaint(new RadialGradientPaint(w/2f,h/2f,w*0.8f,new float[]{0,1},new Color[]{new Color(0,0,0,0),new Color(0,0,0,150)})); g2.fillRect(0,0,w,h);
            for(float[] p:ps){int a=Math.max(0,Math.min(255,(int)(p[4]*255)));g2.setColor(new Color((int)p[5],(int)p[6],(int)p[7],a));g2.fillOval((int)p[0],(int)p[1],(int)p[3],(int)p[3]);}
        }
    }
}
