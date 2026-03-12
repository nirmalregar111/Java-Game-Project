
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameUI extends JFrame {

    private BackgroundPanel bgPanel;
    private TitleLabel titleLabel;
    private List<GlowingButton> buttons;
    private Timer animationTimer;

    public GameUI() {
        setTitle("The Hidden Key");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setUndecorated(true);

        buttons = new ArrayList<>();

        // Background Panel
        bgPanel = new BackgroundPanel("background.jpg");
        bgPanel.setLayout(new GridBagLayout());
        setContentPane(bgPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add spacing at the top
        gbc.insets = new Insets(0, 0, 100, 0);

        // Title Label
        titleLabel = new TitleLabel("THE HIDDEN KEY");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 120));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
        titleLabel.setPreferredSize(new Dimension(1200, 300));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        bgPanel.add(titleLabel, gbc);

        // Space between title and buttons
        gbc.insets = new Insets(15, 0, 15, 0);

        // Buttons
        GlowingButton startButton = createMenuButton("Start Game");

        startButton.addActionListener(e -> {

            String name = JOptionPane.showInputDialog(this, "Enter your name");

            if (name != null && !name.isEmpty()) {
                dispose();
                new GameScreen(name);
            }

        });

        addButton(startButton, gbc);

        GlowingButton instructionButton = createMenuButton("Instructions");

        instructionButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Find the hidden key and escape the dungeon!\n\nControls:\nW A S D - Move\nE - Interact",
                    "Instructions",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        addButton(instructionButton, gbc);

        GlowingButton exitButton = createMenuButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        addButton(exitButton, gbc);

        // 60 FPS Timer (approx 16ms delay)
        animationTimer = new Timer(16, e -> {
            bgPanel.updateAnimations();
            titleLabel.updatePulse();
            for (GlowingButton btn : buttons) {
                btn.updateAnimation();
            }
            // Repaint the background panel which also recursively paints its children
            bgPanel.repaint();
        });
        animationTimer.start();
        setVisible(true);
    }

    private GlowingButton createMenuButton(String text) {
        return new GlowingButton(text);
    }

    private void addButton(GlowingButton button, GridBagConstraints gbc) {
        buttons.add(button);
        bgPanel.add(button, gbc);
    }

    public static void main(String[] args) {
        // Run completely on the EDT
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GameUI();
        });
    }

    // Custom Panel for Background Image, Fog, and Particles
    static class BackgroundPanel extends JPanel {

        private Image backgroundImage;
        private List<Particle> particles;
        private List<FogCloud> fogClouds;
        private Random random = new Random();

        class FogCloud {

            float x, y;
            float speedX;
            float size;
            float alpha;

            FogCloud(int w, int h) {
                reset(Math.max(w, 1920), Math.max(h, 1080), true);
            }

            void reset(int w, int h, boolean randomX) {
                size = 400 + random.nextInt(600);
                x = randomX ? random.nextInt(Math.max(w, 1)) : -size;
                y = h - (size / 2) + random.nextInt(300) - 200; // Mostly near the bottom
                speedX = 0.5f + random.nextFloat() * 1.5f; // Drift right slowly
                alpha = 0.05f + random.nextFloat() * 0.15f;
            }

            void update(int w, int h) {
                if (w <= 0 || h <= 0) {
                    return;
                }
                x += speedX;
                if (x > w + size) {
                    reset(w, h, false);
                }
            }

            void draw(Graphics2D g2) {
                if (alpha <= 0) {
                    return;
                }
                int a = (int) (alpha * 255);
                a = Math.max(0, Math.min(255, a));

                // A very soft, large radial gradient for the fog
                RadialGradientPaint rgp = new RadialGradientPaint(
                        x + size / 2f, y + size / 2f, size / 2f,
                        new float[]{0.0f, 1.0f},
                        new Color[]{new Color(200, 210, 220, a), new Color(200, 210, 220, 0)});
                g2.setPaint(rgp);
                g2.fill(new RoundRectangle2D.Double(x, y, size, size, size, size));
            }
        }

        class Particle {

            float x, y;
            float speedX, speedY;
            float size;
            float alpha;
            Color color;
            float wobblePhase;
            float wobbleSpeed;

            Particle(int w, int h) {
                reset(Math.max(w, 1920), Math.max(h, 1080), true);
            }

            void reset(int w, int h, boolean randomY) {
                x = random.nextInt(Math.max(w, 1));
                y = randomY ? random.nextInt(Math.max(h, 1)) : h + 50;
                speedY = -0.5f - random.nextFloat() * 2.5f; // Float upwards
                speedX = -0.3f + random.nextFloat() * 0.6f; // Drift left/right
                size = 3 + random.nextInt(7); // Ember size
                alpha = 0.2f + random.nextFloat() * 0.7f;
                wobblePhase = random.nextFloat() * (float) Math.PI * 2f;
                wobbleSpeed = 0.02f + random.nextFloat() * 0.05f;

                if (x > w * 0.35 && x < w * 0.65) {
                    // Blue/cyan magic particles for center area
                    int r = 10 + random.nextInt(40);
                    int g = 100 + random.nextInt(100);
                    int b = 200 + random.nextInt(55);
                    color = new Color(r, g, b);
                } else {
                    // Orange/red fiery embers for sides
                    int r = 200 + random.nextInt(55);
                    int g = 40 + random.nextInt(100);
                    int b = random.nextInt(30);
                    color = new Color(r, g, b);
                }
            }

            void update(int w, int h) {
                if (w <= 0 || h <= 0) {
                    return;
                }

                y += speedY;
                x += speedX + (float) Math.sin(wobblePhase) * 0.5f;
                wobblePhase += wobbleSpeed;

                // fade out near the top or gracefully over time
                if (y < h * 0.7f) {
                    alpha -= 0.001f + random.nextFloat() * 0.003f;
                }

                if (y < -50 || alpha <= 0 || x < -50 || x > w + 50) {
                    reset(w, h, false);
                }
            }

            void draw(Graphics2D g2) {
                if (alpha <= 0) {
                    return;
                }
                int a = (int) (alpha * 255);
                a = Math.max(0, Math.min(255, a)); // clamp

                // Draw soft glow
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), a / 3));
                float glowSize = size * 2.5f;
                g2.fill(new RoundRectangle2D.Double(x - glowSize / 2f, y - glowSize / 2f, glowSize, glowSize, glowSize,
                        glowSize));

                // Draw solid ember core
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), a));
                g2.fill(new RoundRectangle2D.Double(x - size / 2f, y - size / 2f, size, size, size, size));
            }
        }

        public BackgroundPanel(String imagePath) {
            setDoubleBuffered(true);

            fogClouds = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                fogClouds.add(new FogCloud(1920, 1080));
            }

            particles = new ArrayList<>();
            for (int i = 0; i < 150; i++) {
                particles.add(new Particle(1920, 1080)); // Initial guess, updates dynamically
            }

            try {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    backgroundImage = ImageIO.read(imgFile);
                } else {
                    System.out.println("Background image '" + imagePath + "' not found. Using dark gradient fallback.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void updateAnimations() {
            int w = getWidth();
            int h = getHeight();
            for (FogCloud f : fogClouds) {
                f.update(w, h);
            }
            for (Particle p : particles) {
                p.update(w, h);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth();
            int h = getHeight();

            Graphics2D g2d = (Graphics2D) g;
            // Enable high quality bilinear filtering
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage, 0, 0, w, h, this);

                // Add a dark overlay so text stands out against the detailed background
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(0, 0, w, h);

                // Vignette effect over the image
                RadialGradientPaint rgp = new RadialGradientPaint(
                        w / 2f, h / 2f, w * 0.9f,
                        new float[]{0.3f, 1.0f},
                        new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 220)});
                g2d.setPaint(rgp);
                g2d.fillRect(0, 0, w, h);
            } else {
                // Fallback Gradient Background (Dark Fantasy Vibe)
                GradientPaint gp = new GradientPaint(
                        w / 2f, 0, new Color(15, 10, 15),
                        w / 2f, h, new Color(40, 5, 10));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                // Add some vignette effect
                RadialGradientPaint rgp = new RadialGradientPaint(
                        w / 2f, h / 2f, w * 0.8f,
                        new float[]{0.0f, 1.0f},
                        new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 220)});
                g2d.setPaint(rgp);
                g2d.fillRect(0, 0, w, h);
            }

            // Draw fog over background
            Graphics2D fogG2d = (Graphics2D) g.create();
            for (FogCloud f : fogClouds) {
                f.draw(fogG2d);
            }
            fogG2d.dispose();

            // Draw particles over fog
            Graphics2D partG2d = (Graphics2D) g.create();
            partG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (Particle p : particles) {
                p.draw(partG2d);
            }
            partG2d.dispose();
        }
    }

    // Custom Cinematic Title with Glow Animation
    static class TitleLabel extends JLabel {

        private float pulsePhase = 0f;

        public TitleLabel(String text) {
            super(text);
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        public void updatePulse() {
            pulsePhase += 0.05f;
            // Phase wraps to avoid extremely large floats, optional but good practice
            if (pulsePhase > Math.PI * 2) {
                pulsePhase -= Math.PI * 2;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            String text = getText();
            FontMetrics fm = g2d.getFontMetrics(getFont());
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();

            // Dark drop shadow
            g2d.setFont(getFont());
            g2d.setColor(new Color(0, 0, 0, 220));
            g2d.drawString(text, x + 8, y + 8);

            // Calculate pulse intensity (0.0 to 1.0)
            float intensity = (float) (Math.sin(pulsePhase) + 1.0) / 2.0f;

            // Outer dynamic red glow
            int glowAlpha = 50 + (int) (120 * intensity);
            g2d.setColor(new Color(180, 20, 20, glowAlpha));

            int offset = 3 + (int) (intensity * 4);
            g2d.drawString(text, x - offset, y - offset);
            g2d.drawString(text, x + offset, y - offset);
            g2d.drawString(text, x - offset, y + offset);
            g2d.drawString(text, x + offset, y + offset);

            // Inner gold glow
            g2d.setColor(new Color(255, 150, 0, 100));
            g2d.drawString(text, x - 1, y - 1);
            g2d.drawString(text, x + 1, y + 1);

            // Main gold text
            g2d.setColor(getForeground());
            g2d.drawString(text, x, y);

            g2d.dispose();
        }
    }

    // Custom Button with Smooth Glowing Hover Effect
    static class GlowingButton extends JButton {

        private boolean isHovered = false;
        private float glowFactor = 0f; // Interpolation factor (0.0 to 1.0)

        private Color normalBg = new Color(15, 10, 15, 200);
        private Color hoverBg = new Color(50, 10, 15, 230);
        private Color normalBorder = new Color(100, 80, 30);
        private Color hoverBorder = new Color(255, 190, 50);

        public GlowingButton(String text) {
            super(text);
            setFont(new Font("Serif", Font.BOLD, 28));
            setForeground(new Color(220, 220, 220));

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);

            // Allow larger preferred size so the outer glow effect doesn't clip
            setPreferredSize(new Dimension(380, 80));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    setForeground(new Color(220, 220, 220));
                }
            });
        }

        public void updateAnimation() {
            if (isHovered && glowFactor < 1f) {
                glowFactor += 0.1f;
                if (glowFactor > 1f) {
                    glowFactor = 1f;
                }
            } else if (!isHovered && glowFactor > 0f) {
                glowFactor -= 0.05f; // Fade out slightly slower
                if (glowFactor < 0f) {
                    glowFactor = 0f;
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Provide margin so the outer glow isn't cut off by component bounds
            int margin = 12;
            int bw = getWidth() - (margin * 2);
            int bh = getHeight() - (margin * 2);

            Shape borderShape = new RoundRectangle2D.Double(margin, margin, bw, bh, 25, 25);

            // Interpolate colors based on glowFactor
            int bgR = (int) (normalBg.getRed() + (hoverBg.getRed() - normalBg.getRed()) * glowFactor);
            int bgG = (int) (normalBg.getGreen() + (hoverBg.getGreen() - normalBg.getGreen()) * glowFactor);
            int bgB = (int) (normalBg.getBlue() + (hoverBg.getBlue() - normalBg.getBlue()) * glowFactor);
            int bgA = (int) (normalBg.getAlpha() + (hoverBg.getAlpha() - normalBg.getAlpha()) * glowFactor);
            Color currentBg = new Color(bgR, bgG, bgB, bgA);

            int bR = (int) (normalBorder.getRed() + (hoverBorder.getRed() - normalBorder.getRed()) * glowFactor);
            int bG = (int) (normalBorder.getGreen() + (hoverBorder.getGreen() - normalBorder.getGreen()) * glowFactor);
            int bB = (int) (normalBorder.getBlue() + (hoverBorder.getBlue() - normalBorder.getBlue()) * glowFactor);
            Color currentBorder = new Color(bR, bG, bB);

            // Outer red glow effect around button
            if (glowFactor > 0f) {
                g2.setColor(new Color(255, 30, 30, (int) (70 * glowFactor)));
                g2.fill(new RoundRectangle2D.Double(margin - 6, margin - 6, bw + 12, bh + 12, 35, 35));
            }

            // Fill button background
            g2.setColor(currentBg);
            g2.fill(borderShape);

            // Draw border outline
            g2.setStroke(new BasicStroke(1.5f + (1.5f * glowFactor)));
            g2.setColor(currentBorder);
            g2.draw(borderShape);

            // Draw text manually centered inside the button boundaries
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics(getFont());
            int textX = margin + (bw - fm.stringWidth(getText())) / 2;
            int textY = margin + ((bh - fm.getHeight()) / 2) + fm.getAscent();

            // Text depth shadow
            g2.setColor(new Color(0, 0, 0, 150));
            g2.drawString(getText(), textX + 2, textY + 2);

            g2.setColor(getForeground());
            g2.drawString(getText(), textX, textY);

            g2.dispose();
        }
    }
}
