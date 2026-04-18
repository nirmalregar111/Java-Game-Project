package game.sprites;

import java.awt.*;
import java.awt.geom.*;

public class SpriteRenderer {

    // ===== PLAYER KNIGHT =====
    public static void drawPlayer(Graphics2D g, int x, int y, int w, int h, float phase, int hp, int maxHp) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float bob = (float) Math.sin(phase * 2) * 4;
        float breathe = (float) Math.sin(phase) * 0.015f + 1.0f;
        int cx = x + w / 2, cy = y + h / 2 + (int) bob;
        float s = Math.min(w, h) / 320f;

        // Health aura
        float hr = hp / (float) maxHp;
        Color aura = hr > 0.5f ? new Color(50, 200, 80, 40) : hr > 0.25f ? new Color(220, 180, 30, 40) : new Color(220, 40, 40, 50);
        g2.setPaint(new RadialGradientPaint(cx, cy, 120 * s, new float[]{0, 1}, new Color[]{aura, new Color(0, 0, 0, 0)}));
        g2.fillOval((int)(cx - 120*s), (int)(cy - 120*s), (int)(240*s), (int)(240*s));

        // Cape
        int[] capeX = {(int)(cx - 25*s), (int)(cx + 25*s), (int)(cx + 35*s + Math.sin(phase*3)*8*s), (int)(cx - 35*s - Math.sin(phase*3+1)*8*s)};
        int[] capeY = {(int)(cy - 30*s), (int)(cy - 30*s), (int)(cy + 80*s), (int)(cy + 75*s)};
        g2.setColor(new Color(160, 20, 20));
        g2.fillPolygon(capeX, capeY, 4);
        g2.setColor(new Color(120, 10, 10));
        g2.drawPolygon(capeX, capeY, 4);

        // Legs
        g2.setColor(new Color(80, 80, 90));
        g2.fill(new RoundRectangle2D.Float(cx - 18*s, cy + 30*s, 14*s, 45*s*breathe, 5*s, 5*s));
        g2.fill(new RoundRectangle2D.Float(cx + 4*s, cy + 30*s, 14*s, 45*s*breathe, 5*s, 5*s));
        // Boots
        g2.setColor(new Color(60, 50, 40));
        g2.fill(new RoundRectangle2D.Float(cx - 20*s, cy + 70*s, 18*s, 10*s, 4*s, 4*s));
        g2.fill(new RoundRectangle2D.Float(cx + 2*s, cy + 70*s, 18*s, 10*s, 4*s, 4*s));

        // Body armor
        g2.setPaint(new GradientPaint(cx - 30*s, cy - 35*s, new Color(170, 175, 185), cx + 30*s, cy + 30*s, new Color(120, 125, 135)));
        g2.fill(new RoundRectangle2D.Float(cx - 28*s, cy - 35*s, 56*s, 68*s, 12*s, 12*s));
        g2.setColor(new Color(90, 95, 105));
        g2.setStroke(new BasicStroke(1.5f * s));
        g2.draw(new RoundRectangle2D.Float(cx - 28*s, cy - 35*s, 56*s, 68*s, 12*s, 12*s));
        // Chest plate line
        g2.drawLine((int)(cx), (int)(cy - 30*s), (int)(cx), (int)(cy + 15*s));
        g2.drawArc((int)(cx - 15*s), (int)(cy - 25*s), (int)(30*s), (int)(20*s), 200, 140);

        // Shield (left arm)
        float shieldAngle = (float) Math.sin(phase * 1.5f) * 0.05f;
        g2.rotate(shieldAngle, cx - 45*s, cy);
        g2.setPaint(new GradientPaint(cx - 60*s, cy - 25*s, new Color(40, 60, 160), cx - 30*s, cy + 25*s, new Color(30, 40, 100)));
        g2.fill(new RoundRectangle2D.Float(cx - 60*s, cy - 25*s, 30*s, 50*s, 8*s, 8*s));
        g2.setColor(new Color(200, 180, 50));
        g2.setStroke(new BasicStroke(2f * s));
        g2.draw(new RoundRectangle2D.Float(cx - 60*s, cy - 25*s, 30*s, 50*s, 8*s, 8*s));
        // Shield cross
        g2.drawLine((int)(cx - 45*s), (int)(cy - 18*s), (int)(cx - 45*s), (int)(cy + 18*s));
        g2.drawLine((int)(cx - 55*s), (int)(cy), (int)(cx - 35*s), (int)(cy));
        g2.rotate(-shieldAngle, cx - 45*s, cy);

        // Sword (right arm)
        float swordAngle = (float) Math.sin(phase * 2) * 0.08f;
        g2.rotate(swordAngle, cx + 40*s, cy - 10*s);
        // Handle
        g2.setColor(new Color(120, 80, 30));
        g2.fill(new RoundRectangle2D.Float(cx + 36*s, cy - 5*s, 8*s, 30*s, 3*s, 3*s));
        // Guard
        g2.setColor(new Color(200, 180, 50));
        g2.fill(new RoundRectangle2D.Float(cx + 30*s, cy - 8*s, 20*s, 5*s, 2*s, 2*s));
        // Blade
        g2.setPaint(new GradientPaint(cx + 37*s, cy - 55*s, new Color(220, 225, 235), cx + 43*s, cy - 10*s, new Color(180, 185, 195)));
        g2.fill(new Polygon(new int[]{(int)(cx+37*s), (int)(cx+43*s), (int)(cx+40*s)},
                new int[]{(int)(cy-10*s), (int)(cy-10*s), (int)(cy-65*s)}, 3));
        g2.rotate(-swordAngle, cx + 40*s, cy - 10*s);

        // Helmet
        g2.setPaint(new GradientPaint(cx - 22*s, cy - 75*s, new Color(160, 165, 175), cx + 22*s, cy - 35*s, new Color(110, 115, 125)));
        g2.fill(new RoundRectangle2D.Float(cx - 22*s, cy - 75*s, 44*s, 42*s, 15*s, 15*s));
        g2.setColor(new Color(80, 85, 95));
        g2.draw(new RoundRectangle2D.Float(cx - 22*s, cy - 75*s, 44*s, 42*s, 15*s, 15*s));
        // Visor T-shape
        g2.setColor(new Color(30, 30, 40));
        g2.setStroke(new BasicStroke(3f * s));
        g2.drawLine((int)(cx - 14*s), (int)(cy - 52*s), (int)(cx + 14*s), (int)(cy - 52*s));
        g2.drawLine((int)(cx), (int)(cy - 52*s), (int)(cx), (int)(cy - 40*s));
        // Eye glow
        int ea = 120 + (int)(80 * Math.sin(phase * 3));
        g2.setColor(new Color(100, 180, 255, ea));
        g2.fillOval((int)(cx - 10*s), (int)(cy - 56*s), (int)(6*s), (int)(4*s));
        g2.fillOval((int)(cx + 4*s), (int)(cy - 56*s), (int)(6*s), (int)(4*s));
        // Plume
        g2.setColor(new Color(180, 30, 30));
        g2.fill(new Polygon(new int[]{(int)(cx), (int)(cx + 5*s), (int)(cx - 5*s)},
                new int[]{(int)(cy - 85*s), (int)(cy - 70*s), (int)(cy - 70*s)}, 3));

        g2.dispose();
    }

    // ===== SKELETON WARRIOR =====
    public static void drawSkeleton(Graphics2D g, int x, int y, int w, int h, float phase) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float sway = (float) Math.sin(phase * 2.5) * 5;
        int cx = x + w / 2 + (int) sway, cy = y + h / 2;
        float s = Math.min(w, h) / 320f;
        Color bone = new Color(230, 220, 200);
        Color boneDark = new Color(180, 170, 150);
        g2.setStroke(new BasicStroke(3f * s));

        // Glow
        g2.setPaint(new RadialGradientPaint(cx, cy, 100*s, new float[]{0,1}, new Color[]{new Color(100,255,100,30), new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-100*s),(int)(cy-100*s),(int)(200*s),(int)(200*s));

        // Legs
        g2.setColor(bone);
        g2.drawLine((int)(cx-8*s),(int)(cy+30*s),(int)(cx-15*s),(int)(cy+75*s));
        g2.drawLine((int)(cx+8*s),(int)(cy+30*s),(int)(cx+15*s),(int)(cy+75*s));

        // Spine
        g2.drawLine((int)(cx),(int)(cy-25*s),(int)(cx),(int)(cy+30*s));

        // Ribs
        for (int i = 0; i < 4; i++) {
            int ry = (int)(cy - 15*s + i * 10*s);
            g2.drawArc((int)(cx-18*s), ry, (int)(18*s), (int)(8*s), 0, -180);
            g2.drawArc((int)(cx), ry, (int)(18*s), (int)(8*s), 180, 180);
        }

        // Arms
        float armSwing = (float)Math.sin(phase*2)*15*s;
        g2.drawLine((int)(cx),(int)(cy-18*s),(int)(cx-35*s),(int)(cy+armSwing));
        g2.drawLine((int)(cx),(int)(cy-18*s),(int)(cx+35*s),(int)(cy-armSwing));
        // Sword in right hand
        g2.setColor(new Color(160,140,120));
        g2.drawLine((int)(cx+35*s),(int)(cy-armSwing),(int)(cx+35*s),(int)(cy-armSwing-40*s));

        // Skull
        g2.setColor(bone);
        g2.fillOval((int)(cx-20*s),(int)(cy-65*s),(int)(40*s),(int)(42*s));
        g2.setColor(boneDark);
        g2.drawOval((int)(cx-20*s),(int)(cy-65*s),(int)(40*s),(int)(42*s));
        // Eye sockets
        g2.setColor(new Color(30,30,20));
        g2.fillOval((int)(cx-12*s),(int)(cy-55*s),(int)(10*s),(int)(10*s));
        g2.fillOval((int)(cx+2*s),(int)(cy-55*s),(int)(10*s),(int)(10*s));
        // Red eye glow
        int eg = (int)(150 + 100*Math.sin(phase*4));
        g2.setColor(new Color(255,50,30,eg));
        g2.fillOval((int)(cx-9*s),(int)(cy-52*s),(int)(4*s),(int)(4*s));
        g2.fillOval((int)(cx+5*s),(int)(cy-52*s),(int)(4*s),(int)(4*s));
        // Nose
        g2.setColor(new Color(30,30,20));
        g2.fill(new Polygon(new int[]{(int)(cx),(int)(cx-4*s),(int)(cx+4*s)},
                new int[]{(int)(cy-40*s),(int)(cy-45*s),(int)(cy-45*s)},3));
        // Jaw
        float jaw = (float)Math.abs(Math.sin(phase*3))*4*s;
        g2.setColor(bone);
        g2.fillArc((int)(cx-15*s),(int)(cy-38*s+jaw),(int)(30*s),(int)(15*s),180,180);
        g2.setColor(new Color(30,30,20));
        g2.drawLine((int)(cx-10*s),(int)(cy-35*s+jaw),(int)(cx+10*s),(int)(cy-35*s+jaw));

        g2.dispose();
    }

    // ===== SHADOW WRAITH =====
    public static void drawWraith(Graphics2D g, int x, int y, int w, int h, float phase) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float floatY = (float) Math.sin(phase * 1.5) * 10;
        int cx = x + w / 2, cy = y + h / 2 + (int) floatY;
        float s = Math.min(w, h) / 320f;

        // Dark aura
        g2.setPaint(new RadialGradientPaint(cx, cy, 130*s, new float[]{0,.6f,1}, new Color[]{new Color(60,0,80,50),new Color(30,0,50,30),new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-130*s),(int)(cy-130*s),(int)(260*s),(int)(260*s));

        // Body wisps
        for (int i = 0; i < 5; i++) {
            float wOff = (float)Math.sin(phase*2 + i*1.3f)*15*s;
            int alpha = 60 - i*10;
            g2.setColor(new Color(40,10,60,Math.max(alpha,10)));
            g2.fill(new Ellipse2D.Float(cx - 20*s + wOff - i*3*s, cy + 20*s + i*12*s, 40*s + i*6*s, 18*s));
        }

        // Main body
        g2.setPaint(new GradientPaint(cx, cy - 60*s, new Color(50,15,70,220), cx, cy + 50*s, new Color(30,5,50,40)));
        g2.fill(new Ellipse2D.Float(cx - 35*s, cy - 60*s, 70*s, 110*s));

        // Hood
        g2.setColor(new Color(25,5,35,240));
        g2.fillArc((int)(cx-30*s),(int)(cy-75*s),(int)(60*s),(int)(50*s),0,180);

        // Eyes - cyan glow
        int eg = (int)(180 + 75*Math.sin(phase*4));
        g2.setColor(new Color(0,220,255,eg));
        g2.fillOval((int)(cx-14*s),(int)(cy-55*s),(int)(10*s),(int)(6*s));
        g2.fillOval((int)(cx+4*s),(int)(cy-55*s),(int)(10*s),(int)(6*s));
        // Eye glow effect
        g2.setPaint(new RadialGradientPaint(cx, cy-52*s, 25*s, new float[]{0,1}, new Color[]{new Color(0,220,255,eg/3),new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-25*s),(int)(cy-65*s),(int)(50*s),(int)(25*s));

        g2.dispose();
    }

    // ===== CAVE TROLL =====
    public static void drawTroll(Graphics2D g, int x, int y, int w, int h, float phase) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float breathe = (float) Math.sin(phase) * 3;
        int cx = x + w / 2, cy = y + h / 2;
        float s = Math.min(w, h) / 320f;

        // Legs
        g2.setColor(new Color(80,100,60));
        g2.fill(new RoundRectangle2D.Float(cx - 25*s, cy + 25*s, 20*s, 50*s, 8*s, 8*s));
        g2.fill(new RoundRectangle2D.Float(cx + 5*s, cy + 25*s, 20*s, 50*s, 8*s, 8*s));

        // Body (large)
        g2.setPaint(new GradientPaint(cx, cy - 50*s, new Color(100,130,70), cx, cy + 30*s, new Color(70,95,50)));
        g2.fill(new Ellipse2D.Float(cx - 45*s, cy - 50*s + breathe, 90*s, 80*s));
        g2.setColor(new Color(60,80,40));
        g2.setStroke(new BasicStroke(2*s));
        g2.draw(new Ellipse2D.Float(cx - 45*s, cy - 50*s + breathe, 90*s, 80*s));
        // Belly
        g2.setColor(new Color(120,150,85));
        g2.fill(new Ellipse2D.Float(cx - 25*s, cy - 20*s + breathe, 50*s, 40*s));

        // Arms
        g2.setColor(new Color(90,115,60));
        g2.fill(new RoundRectangle2D.Float(cx - 60*s, cy - 30*s, 20*s, 55*s, 10*s, 10*s));
        // Club in right hand
        float clubSwing = (float) Math.sin(phase * 2) * 0.15f;
        g2.rotate(clubSwing, cx + 50*s, cy - 20*s);
        g2.fill(new RoundRectangle2D.Float(cx + 40*s, cy - 30*s, 20*s, 55*s, 10*s, 10*s));
        g2.setColor(new Color(100,70,40));
        g2.fill(new RoundRectangle2D.Float(cx + 43*s, cy - 70*s, 14*s, 50*s, 5*s, 5*s));
        g2.setColor(new Color(80,55,30));
        g2.fill(new Ellipse2D.Float(cx + 38*s, cy - 85*s, 24*s, 22*s));
        g2.rotate(-clubSwing, cx + 50*s, cy - 20*s);

        // Head
        g2.setPaint(new GradientPaint(cx, cy - 80*s, new Color(95,120,65), cx, cy - 45*s, new Color(80,105,55)));
        g2.fill(new Ellipse2D.Float(cx - 28*s, cy - 85*s, 56*s, 40*s));
        // Brow
        g2.setColor(new Color(70,90,45));
        g2.fillArc((int)(cx-25*s),(int)(cy-80*s),(int)(50*s),(int)(15*s),0,180);
        // Eyes (small)
        g2.setColor(new Color(220,200,50));
        g2.fillOval((int)(cx-14*s),(int)(cy-72*s),(int)(8*s),(int)(6*s));
        g2.fillOval((int)(cx+6*s),(int)(cy-72*s),(int)(8*s),(int)(6*s));
        g2.setColor(Color.BLACK);
        g2.fillOval((int)(cx-11*s),(int)(cy-70*s),(int)(3*s),(int)(3*s));
        g2.fillOval((int)(cx+9*s),(int)(cy-70*s),(int)(3*s),(int)(3*s));
        // Mouth with teeth
        g2.setColor(new Color(60,30,20));
        g2.fillArc((int)(cx-18*s),(int)(cy-62*s),(int)(36*s),(int)(18*s),180,180);
        g2.setColor(new Color(230,220,200));
        g2.fillRect((int)(cx-8*s),(int)(cy-62*s),(int)(5*s),(int)(6*s));
        g2.fillRect((int)(cx+3*s),(int)(cy-62*s),(int)(5*s),(int)(6*s));

        g2.dispose();
    }

    // ===== DARK SPIDER =====
    public static void drawSpider(Graphics2D g, int x, int y, int w, int h, float phase) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float pulse = (float) Math.sin(phase * 2) * 3;
        int cx = x + w / 2, cy = y + h / 2;
        float s = Math.min(w, h) / 320f;

        // Legs (8)
        g2.setColor(new Color(30,25,35));
        g2.setStroke(new BasicStroke(3*s));
        for (int i = 0; i < 4; i++) {
            float legPhase = phase * 3 + i * 0.8f;
            float legMove = (float) Math.sin(legPhase) * 8 * s;
            int lx = (int)(cx - 30*s - i*5*s);
            int rx = (int)(cx + 30*s + i*5*s);
            int ly = (int)(cy - 10*s + i*12*s);
            // Left legs (with joint)
            g2.drawLine(lx, ly, (int)(lx - 30*s), (int)(ly - 20*s + legMove));
            g2.drawLine((int)(lx - 30*s), (int)(ly - 20*s + legMove), (int)(lx - 50*s), (int)(ly + 15*s + legMove));
            // Right legs
            g2.drawLine(rx, ly, (int)(rx + 30*s), (int)(ly - 20*s - legMove));
            g2.drawLine((int)(rx + 30*s), (int)(ly - 20*s - legMove), (int)(rx + 50*s), (int)(ly + 15*s - legMove));
        }

        // Abdomen
        g2.setPaint(new GradientPaint(cx, cy + 10*s, new Color(40,30,50), cx, cy + 55*s, new Color(25,18,30)));
        g2.fill(new Ellipse2D.Float(cx - 30*s, cy + 5*s + pulse, 60*s, 50*s));
        // Red hourglass
        g2.setColor(new Color(200,30,30));
        g2.fill(new Polygon(new int[]{(int)(cx),(int)(cx-8*s),(int)(cx+8*s)},new int[]{(int)(cy+30*s+pulse),(int)(cy+18*s+pulse),(int)(cy+18*s+pulse)},3));
        g2.fill(new Polygon(new int[]{(int)(cx),(int)(cx-8*s),(int)(cx+8*s)},new int[]{(int)(cy+30*s+pulse),(int)(cy+42*s+pulse),(int)(cy+42*s+pulse)},3));

        // Body (cephalothorax)
        g2.setPaint(new GradientPaint(cx, cy - 30*s, new Color(50,40,60), cx, cy + 10*s, new Color(35,28,42)));
        g2.fill(new Ellipse2D.Float(cx - 28*s, cy - 35*s, 56*s, 48*s));

        // Eyes (8 red dots)
        g2.setColor(new Color(255,30,20,(int)(180+60*Math.sin(phase*4))));
        int[][] eyes = {{-12,-22,4},{-5,-25,5},{5,-25,5},{12,-22,4},{-8,-18,3},{8,-18,3},{-4,-15,2},{4,-15,2}};
        for (int[] e : eyes) {
            g2.fillOval((int)(cx+e[0]*s),(int)(cy+e[1]*s),(int)(e[2]*s),(int)(e[2]*s));
        }
        // Fangs
        g2.setColor(new Color(200,190,170));
        g2.setStroke(new BasicStroke(2.5f*s));
        g2.drawLine((int)(cx-6*s),(int)(cy-10*s),(int)(cx-10*s),(int)(cy+2*s));
        g2.drawLine((int)(cx+6*s),(int)(cy-10*s),(int)(cx+10*s),(int)(cy+2*s));

        g2.dispose();
    }

    // ===== CURSED KNIGHT =====
    public static void drawCursedKnight(Graphics2D g, int x, int y, int w, int h, float phase) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = x + w / 2, cy = y + h / 2;
        float s = Math.min(w, h) / 320f;
        float pulse = (float) Math.sin(phase * 2) * 3;

        // Dark aura
        int aa = (int)(40 + 20*Math.sin(phase*3));
        g2.setPaint(new RadialGradientPaint(cx, cy, 110*s, new float[]{0,1}, new Color[]{new Color(180,0,0,aa),new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-110*s),(int)(cy-110*s),(int)(220*s),(int)(220*s));

        // Legs
        g2.setColor(new Color(40,20,25));
        g2.fill(new RoundRectangle2D.Float(cx-18*s,cy+30*s,14*s,45*s,5*s,5*s));
        g2.fill(new RoundRectangle2D.Float(cx+4*s,cy+30*s,14*s,45*s,5*s,5*s));

        // Body
        g2.setPaint(new GradientPaint(cx,cy-35*s,new Color(50,20,25),cx,cy+30*s,new Color(30,10,15)));
        g2.fill(new RoundRectangle2D.Float(cx-28*s,cy-35*s+pulse,56*s,68*s,12*s,12*s));
        g2.setColor(new Color(80,30,30));
        g2.setStroke(new BasicStroke(1.5f*s));
        g2.draw(new RoundRectangle2D.Float(cx-28*s,cy-35*s+pulse,56*s,68*s,12*s,12*s));
        // Damage marks
        g2.setColor(new Color(100,40,40));
        g2.drawLine((int)(cx-15*s),(int)(cy-20*s+pulse),(int)(cx-5*s),(int)(cy-5*s+pulse));
        g2.drawLine((int)(cx+10*s),(int)(cy+5*s+pulse),(int)(cx+20*s),(int)(cy+15*s+pulse));

        // Dark sword with red glow
        float sw = (float)Math.sin(phase*2)*0.1f;
        g2.rotate(sw, cx+40*s, cy);
        g2.setColor(new Color(180,20,20,80));
        g2.fill(new RoundRectangle2D.Float(cx+33*s,cy-70*s,14*s,65*s,4*s,4*s));
        g2.setColor(new Color(20,10,15));
        g2.fill(new Polygon(new int[]{(int)(cx+37*s),(int)(cx+43*s),(int)(cx+40*s)},new int[]{(int)(cy-8*s),(int)(cy-8*s),(int)(cy-65*s)},3));
        g2.setColor(new Color(40,20,20));
        g2.fill(new RoundRectangle2D.Float(cx+36*s,cy-5*s,8*s,28*s,3*s,3*s));
        g2.rotate(-sw, cx+40*s, cy);

        // Helmet
        g2.setPaint(new GradientPaint(cx,cy-75*s,new Color(45,18,22),cx,cy-35*s,new Color(30,10,14)));
        g2.fill(new RoundRectangle2D.Float(cx-22*s,cy-75*s,44*s,42*s,15*s,15*s));
        g2.setColor(new Color(60,25,25));
        g2.draw(new RoundRectangle2D.Float(cx-22*s,cy-75*s,44*s,42*s,15*s,15*s));
        // Red eyes
        int eg = (int)(200 + 55*Math.sin(phase*5));
        g2.setColor(new Color(255,20,20,eg));
        g2.fillOval((int)(cx-11*s),(int)(cy-57*s),(int)(8*s),(int)(5*s));
        g2.fillOval((int)(cx+3*s),(int)(cy-57*s),(int)(8*s),(int)(5*s));
        // Eye glow
        g2.setPaint(new RadialGradientPaint(cx,cy-54*s,20*s,new float[]{0,1},new Color[]{new Color(255,20,20,eg/3),new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-20*s),(int)(cy-64*s),(int)(40*s),(int)(20*s));
        // Horns
        g2.setColor(new Color(40,15,18));
        g2.fill(new Polygon(new int[]{(int)(cx-20*s),(int)(cx-15*s),(int)(cx-30*s)},new int[]{(int)(cy-70*s),(int)(cy-65*s),(int)(cy-90*s)},3));
        g2.fill(new Polygon(new int[]{(int)(cx+20*s),(int)(cx+15*s),(int)(cx+30*s)},new int[]{(int)(cy-70*s),(int)(cy-65*s),(int)(cy-90*s)},3));

        g2.dispose();
    }

    // ===== BOSS =====
    public static void drawBoss(Graphics2D g, int x, int y, int w, int h, String name, int hp, int maxHp, float phase) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = x + w/2, cy = y + h/2;
        float s = Math.min(w, h) / 300f;

        // Massive dark aura
        int aa = (int)(50+30*Math.sin(phase*2));
        g2.setPaint(new RadialGradientPaint(cx, cy, 150*s, new float[]{0,.5f,1},
                new Color[]{new Color(150,0,0,aa), new Color(80,0,50,aa/2), new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-150*s),(int)(cy-150*s),(int)(300*s),(int)(300*s));

        float breathe = (float)Math.sin(phase)*3;

        // Large body
        g2.setPaint(new GradientPaint(cx, cy-50*s, new Color(35,10,15), cx, cy+40*s, new Color(20,5,8)));
        g2.fill(new RoundRectangle2D.Float(cx-40*s, cy-50*s+breathe, 80*s, 90*s, 15*s, 15*s));

        // Shoulder armor / spikes
        g2.setColor(new Color(50,15,20));
        g2.fill(new Polygon(new int[]{(int)(cx-40*s),(int)(cx-55*s),(int)(cx-30*s)},
                new int[]{(int)(cy-40*s+breathe),(int)(cy-65*s),(int)(cy-50*s+breathe)},3));
        g2.fill(new Polygon(new int[]{(int)(cx+40*s),(int)(cx+55*s),(int)(cx+30*s)},
                new int[]{(int)(cy-40*s+breathe),(int)(cy-65*s),(int)(cy-50*s+breathe)},3));

        // Arms
        g2.setColor(new Color(30,8,12));
        g2.fill(new RoundRectangle2D.Float(cx-60*s, cy-30*s+breathe, 22*s, 60*s, 8*s, 8*s));
        float swordSwing = (float)Math.sin(phase*2)*0.2f;
        g2.rotate(swordSwing, cx+50*s, cy-20*s);
        g2.fill(new RoundRectangle2D.Float(cx+38*s, cy-30*s+breathe, 22*s, 60*s, 8*s, 8*s));
        // Boss sword
        g2.setColor(new Color(150,10,10,150));
        g2.fill(new RoundRectangle2D.Float(cx+42*s,cy-85*s,14*s,60*s,3*s,3*s));
        g2.setColor(new Color(60,10,15));
        g2.fill(new Polygon(new int[]{(int)(cx+45*s),(int)(cx+53*s),(int)(cx+49*s)},
                new int[]{(int)(cy-28*s),(int)(cy-28*s),(int)(cy-85*s)},3));
        g2.rotate(-swordSwing, cx+50*s, cy-20*s);

        // Legs
        g2.setColor(new Color(25,8,10));
        g2.fill(new RoundRectangle2D.Float(cx-22*s, cy+35*s, 18*s, 45*s, 6*s, 6*s));
        g2.fill(new RoundRectangle2D.Float(cx+4*s, cy+35*s, 18*s, 45*s, 6*s, 6*s));

        // Head
        g2.setPaint(new GradientPaint(cx,cy-90*s,new Color(40,12,16),cx,cy-50*s,new Color(28,8,10)));
        g2.fill(new RoundRectangle2D.Float(cx-25*s,cy-90*s,50*s,45*s,18*s,18*s));

        // Crown
        g2.setColor(new Color(200,170,30));
        int crownY = (int)(cy-95*s);
        g2.fill(new Polygon(new int[]{(int)(cx-22*s),(int)(cx+22*s),(int)(cx+18*s),(int)(cx+10*s),(int)(cx),(int)(cx-10*s),(int)(cx-18*s)},
                new int[]{crownY, crownY, crownY-15, crownY-8, crownY-18, crownY-8, crownY-15},7));
        // Gems
        g2.setColor(new Color(220,30,30));
        g2.fillOval((int)(cx-4*s),crownY-14,(int)(8*s),(int)(6*s));
        g2.setColor(new Color(30,100,220));
        g2.fillOval((int)(cx-16*s),crownY-10,(int)(5*s),(int)(4*s));
        g2.fillOval((int)(cx+11*s),crownY-10,(int)(5*s),(int)(4*s));

        // Eyes
        int eg = (int)(200+55*Math.sin(phase*4));
        g2.setColor(new Color(255,0,0,eg));
        g2.fillOval((int)(cx-14*s),(int)(cy-78*s),(int)(10*s),(int)(7*s));
        g2.fillOval((int)(cx+4*s),(int)(cy-78*s),(int)(10*s),(int)(7*s));
        g2.setPaint(new RadialGradientPaint(cx,cy-74*s,25*s,new float[]{0,1},new Color[]{new Color(255,0,0,eg/3),new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-25*s),(int)(cy-85*s),(int)(50*s),(int)(22*s));

        // Health bar above boss
        int barW = (int)(80*s), barH = (int)(8*s), barX = cx-barW/2, barY = (int)(cy-110*s);
        g2.setColor(new Color(40,10,10));
        g2.fillRect(barX,barY,barW,barH);
        float hpRatio = hp/(float)maxHp;
        g2.setColor(hpRatio > 0.5f ? new Color(200,30,30) : hpRatio > 0.25f ? new Color(200,100,30) : new Color(200,200,30));
        g2.fillRect(barX,barY,(int)(barW*hpRatio),barH);
        g2.setColor(new Color(200,170,30,150));
        g2.drawRect(barX,barY,barW,barH);

        g2.dispose();
    }

    // ===== TREASURE CHEST =====
    public static void drawTreasure(Graphics2D g, int x, int y, int w, int h, boolean opened, float phase) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = x + w/2, cy = y + h/2 + 20;
        float s = Math.min(w, h) / 300f;

        // Gold glow
        int ga = (int)(40 + 30*Math.sin(phase*2));
        g2.setPaint(new RadialGradientPaint(cx, cy-20*s, 100*s, new float[]{0,1},
                new Color[]{new Color(255,200,50,ga), new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-100*s),(int)(cy-120*s),(int)(200*s),(int)(200*s));

        // Chest body
        g2.setPaint(new GradientPaint(cx,cy-10*s,new Color(140,80,30),cx,cy+45*s,new Color(100,55,20)));
        g2.fill(new RoundRectangle2D.Float(cx-45*s,cy-10*s,90*s,55*s,10*s,10*s));
        // Wood slats
        g2.setColor(new Color(110,60,20));
        g2.setStroke(new BasicStroke(1.5f*s));
        g2.drawLine((int)(cx-40*s),(int)(cy+10*s),(int)(cx+40*s),(int)(cy+10*s));
        g2.drawLine((int)(cx-40*s),(int)(cy+28*s),(int)(cx+40*s),(int)(cy+28*s));

        // Metal bands
        g2.setColor(new Color(160,140,50));
        g2.setStroke(new BasicStroke(3*s));
        g2.draw(new RoundRectangle2D.Float(cx-45*s,cy-10*s,90*s,55*s,10*s,10*s));
        g2.drawLine((int)(cx),(int)(cy-10*s),(int)(cx),(int)(cy+45*s));

        // Lid
        if (opened) {
            g2.setPaint(new GradientPaint(cx,cy-50*s,new Color(140,80,30),cx,cy-10*s,new Color(120,65,25)));
            g2.fill(new RoundRectangle2D.Float(cx-48*s,cy-55*s,96*s,30*s,10*s,10*s));
            // Gold coins peeking out
            g2.setColor(new Color(255,215,0));
            for (int i = 0; i < 5; i++) {
                float ox = (float)Math.sin(phase*2+i*1.5f)*3*s;
                g2.fillOval((int)(cx-20*s+i*10*s+ox),(int)(cy-18*s),(int)(10*s),(int)(10*s));
            }
            // Sparkle
            drawSparkles(g2,cx,cy-30*s,40*s,phase);
        } else {
            // Closed lid
            g2.setPaint(new GradientPaint(cx,cy-35*s,new Color(140,80,30),cx,cy-10*s,new Color(120,65,25)));
            g2.fillArc((int)(cx-45*s),(int)(cy-35*s),(int)(90*s),(int)(50*s),0,180);
            g2.setColor(new Color(160,140,50));
            g2.setStroke(new BasicStroke(3*s));
            g2.drawArc((int)(cx-45*s),(int)(cy-35*s),(int)(90*s),(int)(50*s),0,180);
        }

        // Lock/keyhole
        g2.setColor(new Color(180,160,60));
        g2.fillOval((int)(cx-6*s),(int)(cy+2*s),(int)(12*s),(int)(12*s));
        g2.setColor(new Color(30,20,10));
        g2.fill(new Polygon(new int[]{(int)(cx),(int)(cx-3*s),(int)(cx+3*s)},
                new int[]{(int)(cy+12*s),(int)(cy+6*s),(int)(cy+6*s)},3));
        g2.fillOval((int)(cx-2*s),(int)(cy+4*s),(int)(4*s),(int)(4*s));

        g2.dispose();
    }

    // ===== PUZZLE RUNE =====
    public static void drawPuzzleRune(Graphics2D g, int x, int y, int w, int h, float phase) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = x + w/2, cy = y + h/2;
        float s = Math.min(w, h) / 300f;

        // Mystical glow
        int ga = (int)(50+40*Math.sin(phase*1.5));
        g2.setPaint(new RadialGradientPaint(cx, cy, 120*s, new float[]{0,.5f,1},
                new Color[]{new Color(100,50,200,ga), new Color(50,20,150,ga/2), new Color(0,0,0,0)}));
        g2.fillOval((int)(cx-120*s),(int)(cy-120*s),(int)(240*s),(int)(240*s));

        // Rotating outer ring
        g2.rotate(phase*0.3, cx, cy);
        g2.setColor(new Color(150,80,220,(int)(150+50*Math.sin(phase*2))));
        g2.setStroke(new BasicStroke(3*s));
        g2.drawOval((int)(cx-60*s),(int)(cy-60*s),(int)(120*s),(int)(120*s));
        // Rune marks on ring
        for (int i = 0; i < 8; i++) {
            double a = i * Math.PI / 4;
            int rx = (int)(cx + 60*s*Math.cos(a));
            int ry = (int)(cy + 60*s*Math.sin(a));
            g2.fillOval(rx-3, ry-3, 6, 6);
        }
        g2.rotate(-phase*0.3, cx, cy);

        // Inner ring (counter-rotate)
        g2.rotate(-phase*0.5, cx, cy);
        g2.setColor(new Color(180,120,255,(int)(120+60*Math.sin(phase*3))));
        g2.setStroke(new BasicStroke(2*s));
        g2.drawOval((int)(cx-38*s),(int)(cy-38*s),(int)(76*s),(int)(76*s));
        // Triangle
        int ts = (int)(32*s);
        g2.draw(new Polygon(new int[]{cx, cx-ts, cx+ts}, new int[]{cy-ts, cy+ts/2, cy+ts/2}, 3));
        g2.rotate(phase*0.5, cx, cy);

        // Center eye symbol
        g2.setColor(new Color(200,150,255,(int)(200+55*Math.sin(phase*4))));
        g2.fillOval((int)(cx-15*s),(int)(cy-10*s),(int)(30*s),(int)(20*s));
        g2.setColor(new Color(50,0,100));
        g2.fillOval((int)(cx-7*s),(int)(cy-5*s),(int)(14*s),(int)(10*s));
        g2.setColor(new Color(255,200,255));
        g2.fillOval((int)(cx-3*s),(int)(cy-2*s),(int)(6*s),(int)(4*s));

        // Floating particles
        drawSparkles(g2,cx,cy,70*s,phase);

        g2.dispose();
    }

    // Helper: sparkle particles
    private static void drawSparkles(Graphics2D g2, float cx, float cy, float radius, float phase) {
        for (int i = 0; i < 8; i++) {
            double angle = phase * 1.5 + i * Math.PI / 4;
            float dist = radius * (0.5f + 0.5f * (float) Math.sin(phase * 2 + i));
            float px = cx + (float) Math.cos(angle) * dist;
            float py = cy + (float) Math.sin(angle) * dist;
            int alpha = (int) (100 + 100 * Math.sin(phase * 3 + i * 0.7));
            alpha = Math.max(0, Math.min(255, alpha));
            g2.setColor(new Color(255, 230, 150, alpha));
            g2.fillOval((int) px - 2, (int) py - 2, 4, 4);
            g2.setColor(new Color(255, 255, 200, alpha / 2));
            g2.fillOval((int) px - 4, (int) py - 4, 8, 8);
        }
    }

    // ===== DISPATCH by monster name =====
    public static void drawMonster(Graphics2D g, int x, int y, int w, int h, String monsterName, float phase) {
        if (monsterName == null) return;
        String lower = monsterName.toLowerCase();
        if (lower.contains("skeleton")) drawSkeleton(g, x, y, w, h, phase);
        else if (lower.contains("wraith") || lower.contains("shadow")) drawWraith(g, x, y, w, h, phase);
        else if (lower.contains("troll")) drawTroll(g, x, y, w, h, phase);
        else if (lower.contains("spider")) drawSpider(g, x, y, w, h, phase);
        else if (lower.contains("knight") || lower.contains("cursed")) drawCursedKnight(g, x, y, w, h, phase);
        else drawSkeleton(g, x, y, w, h, phase); // fallback
    }
}
