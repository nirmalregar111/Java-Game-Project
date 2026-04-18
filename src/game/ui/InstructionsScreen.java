package game.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import game.audio.SoundManager;

public class InstructionsScreen extends JFrame {
    private Timer animTimer;
    private IBgPanel bgPanel;

    public InstructionsScreen() {
        setTitle("The Hidden Key - Instructions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        bgPanel = new IBgPanel();
        bgPanel.setLayout(new GridBagLayout());
        setContentPane(bgPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("HOW TO PLAY");
        title.setFont(new Font("Serif", Font.BOLD, 72));
        title.setForeground(new Color(255, 215, 0));
        gbc.insets = new Insets(20, 0, 30, 0);
        bgPanel.add(title, gbc);

        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(10, 8, 20, 210));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2.setColor(new Color(255, 215, 0, 50));
                g2.setStroke(new BasicStroke(2f));
                g2.draw(new RoundRectangle2D.Double(2, 2, getWidth() - 4, getHeight() - 4, 30, 30));
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(900, 520));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        addSec(card, "\uD83D\uDCDC THE STORY",
            "You are trapped in a cursed dungeon. Deep within lies the Hidden Key \u2014\n"
            + "the only way to escape. Navigate through dangerous rooms, solve ancient\n"
            + "puzzles, collect treasures, and defeat the final Boss to claim your freedom!",
            new Color(200, 180, 140));
        addDiv(card);
        addSec(card, "\uD83D\uDEAA ROOM TYPES",
            "\uD83D\uDD25 Danger Room  \u2014  Fight monsters or run away\n"
            + "\uD83D\uDC8E Treasure Room  \u2014  Open chests for score, potions & the Key\n"
            + "\uD83E\uDDE9 Puzzle Room  \u2014  Answer riddles correctly for bonuses\n"
            + "\uD83D\uDC51 Boss Room  \u2014  The final battle to get the Key!",
            new Color(180, 200, 220));
        addDiv(card);
        addSec(card, "\uD83D\uDCA1 TIPS",
            "\u2022 Collect potions \u2014 you'll need them for the Boss!\n"
            + "\u2022 Running away still costs some health\n"
            + "\u2022 The Key can appear in Treasure, Puzzle, or Boss rooms\n"
            + "\u2022 Use Dodge in Boss fights for counter-attack chances!",
            new Color(180, 220, 180));

        gbc.insets = new Insets(0, 0, 20, 0);
        bgPanel.add(card, gbc);

        JButton backBtn = mkGlowBtn("\u2B05 Back to Menu");
        backBtn.addActionListener(e -> { SoundManager.playClick(); animTimer.stop(); dispose(); SwingUtilities.invokeLater(GameUI::new); });
        gbc.insets = new Insets(20, 0, 30, 0);
        bgPanel.add(backBtn, gbc);

        animTimer = new Timer(16, e -> { bgPanel.update(); bgPanel.repaint(); });
        animTimer.start();
        setVisible(true);
    }

    private void addSec(JPanel p, String title, String body, Color c) {
        JLabel tl = new JLabel(title);
        tl.setFont(new Font("Serif", Font.BOLD, 24));
        tl.setForeground(new Color(255, 215, 0));
        tl.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(tl);
        p.add(Box.createVerticalStrut(8));
        JTextArea ta = new JTextArea(body);
        ta.setFont(new Font("Serif", Font.PLAIN, 17));
        ta.setForeground(c);
        ta.setOpaque(false); ta.setEditable(false); ta.setFocusable(false);
        ta.setLineWrap(true); ta.setWrapStyleWord(true);
        ta.setAlignmentX(Component.LEFT_ALIGNMENT);
        ta.setMaximumSize(new Dimension(800, 200));
        p.add(ta);
    }

    private void addDiv(JPanel p) {
        p.add(Box.createVerticalStrut(15));
        JPanel line = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                ((Graphics2D) g).setColor(new Color(255, 215, 0, 30));
                g.fillRect(0, 0, getWidth(), 1);
            }
        };
        line.setOpaque(false);
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        line.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(line);
        p.add(Box.createVerticalStrut(15));
    }

    private JButton mkGlowBtn(String text) {
        JButton btn = new JButton(text) {
            boolean hov = false; float gf = 0;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true; }
                public void mouseExited(MouseEvent e) { hov = false; }
            });
            Timer t = new Timer(16, e -> {
                if (hov && gf < 1) gf = Math.min(1, gf + 0.1f);
                else if (!hov && gf > 0) gf = Math.max(0, gf - 0.06f);
                repaint();
            }); t.start(); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight(), m = 8;
                if (gf > 0) { g2.setColor(new Color(255, 50, 30, (int)(60*gf))); g2.fill(new RoundRectangle2D.Double(m-5,m-5,w-m*2+10,h-m*2+10,30,30)); }
                g2.setColor(new Color(15+(int)(30*gf),10,15,(int)(200+30*gf)));
                g2.fill(new RoundRectangle2D.Double(m,m,w-m*2,h-m*2,25,25));
                g2.setColor(new Color((int)(100+155*gf),(int)(80+110*gf),(int)(30+20*gf)));
                g2.setStroke(new BasicStroke(1.5f+gf)); g2.draw(new RoundRectangle2D.Double(m,m,w-m*2,h-m*2,25,25));
                FontMetrics fm = g2.getFontMetrics(getFont()); int tx = (w-fm.stringWidth(getText()))/2, ty = (h-fm.getHeight())/2+fm.getAscent();
                g2.setColor(new Color(0,0,0,150)); g2.drawString(getText(),tx+2,ty+2);
                g2.setColor(getForeground()); g2.drawString(getText(),tx,ty);
            }
        };
        btn.setFont(new Font("Serif", Font.BOLD, 28));
        btn.setForeground(new Color(220, 220, 220));
        btn.setPreferredSize(new Dimension(380, 75));
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false); btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    static class IBgPanel extends JPanel {
        List<float[]> ps = new ArrayList<>(); Random r = new Random();
        IBgPanel() { setDoubleBuffered(true); for (int i = 0; i < 80; i++) ps.add(new float[]{r.nextInt(1920),r.nextInt(1080),-0.2f-r.nextFloat()*1.2f,2+r.nextInt(4),0.1f+r.nextFloat()*0.3f,30+r.nextInt(40),80+r.nextInt(60),160+r.nextInt(80)}); }
        void update() { for (float[] p : ps) { p[1] += p[2]; if (p[1] < -10) { p[1] = getHeight() + 10; p[0] = r.nextInt(Math.max(getWidth(), 1)); } } }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g); int w = getWidth(), h = getHeight(); Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(new GradientPaint(w/2f,0,new Color(8,5,18),w/2f,h,new Color(15,8,12))); g2.fillRect(0,0,w,h);
            g2.setPaint(new RadialGradientPaint(w/2f,h/2f,w*0.7f,new float[]{0,1},new Color[]{new Color(0,0,0,0),new Color(0,0,0,180)})); g2.fillRect(0,0,w,h);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (float[] p : ps) { int a = Math.max(0,Math.min(255,(int)(p[4]*255))); g2.setColor(new Color((int)p[5],(int)p[6],(int)p[7],a)); g2.fillOval((int)p[0],(int)p[1],(int)p[3],(int)p[3]); }
        }
    }
}
