package game.ui;

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
import game.audio.SoundManager;

public class GameUI extends JFrame {

    private BgPanel bgPanel;
    private TitleLabel titleLabel;
    private List<GlowBtn> buttons;
    private Timer animTimer;

    public GameUI() {
        setTitle("The Hidden Key");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        buttons = new ArrayList<>();

        bgPanel = new BgPanel("assets/Background.jpg");
        bgPanel.setLayout(new GridBagLayout());
        setContentPane(bgPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 100, 0);

        titleLabel = new TitleLabel("THE HIDDEN KEY");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 120));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setPreferredSize(new Dimension(1200, 300));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        bgPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(15, 0, 15, 0);

        GlowBtn startBtn = new GlowBtn("Start Game");
        startBtn.addActionListener(e -> { SoundManager.playClick(); showNameDialog(); });
        addBtn(startBtn, gbc);

        GlowBtn instrBtn = new GlowBtn("Instructions");
        instrBtn.addActionListener(e -> { SoundManager.playClick(); animTimer.stop(); dispose(); SwingUtilities.invokeLater(InstructionsScreen::new); });
        addBtn(instrBtn, gbc);

        GlowBtn exitBtn = new GlowBtn("Exit");
        exitBtn.addActionListener(e -> { SoundManager.playClick(); System.exit(0); });
        addBtn(exitBtn, gbc);

        animTimer = new Timer(16, e -> {
            bgPanel.update(); titleLabel.pulse();
            for (GlowBtn b : buttons) b.tick();
            bgPanel.repaint();
        });
        animTimer.start();
        SoundManager.playTheme();
        setVisible(true);
    }

    private void addBtn(GlowBtn b, GridBagConstraints gbc) { buttons.add(b); bgPanel.add(b, gbc); }

    private void showNameDialog() {
        JDialog d = new JDialog(this, "Enter Your Name", true);
        d.setUndecorated(true); d.setSize(500, 300); d.setLocationRelativeTo(this);
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0,0,new Color(15,10,20),0,getHeight(),new Color(25,10,15)));
                g2.fill(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),30,30));
                g2.setColor(new Color(255,215,0,80)); g2.setStroke(new BasicStroke(2.5f));
                g2.draw(new RoundRectangle2D.Double(2,2,getWidth()-4,getHeight()-4,30,30));
            }
        };
        p.setLayout(new GridBagLayout()); p.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridwidth = GridBagConstraints.REMAINDER; gc.anchor = GridBagConstraints.CENTER;

        JLabel tl = new JLabel("Enter Your Name, Adventurer");
        tl.setFont(new Font("Serif", Font.BOLD, 28)); tl.setForeground(new Color(255,215,0));
        gc.insets = new Insets(20,0,20,0); p.add(tl, gc);

        JTextField nf = new JTextField(20);
        nf.setFont(new Font("Serif", Font.BOLD, 22)); nf.setBackground(new Color(30,20,40));
        nf.setForeground(new Color(255,215,0)); nf.setCaretColor(new Color(255,215,0));
        nf.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100,80,30),2),BorderFactory.createEmptyBorder(8,15,8,15)));
        nf.setHorizontalAlignment(JTextField.CENTER);
        gc.insets = new Insets(0,30,20,30); gc.fill = GridBagConstraints.HORIZONTAL; p.add(nf, gc);

        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); bp.setOpaque(false);
        JButton playB = mkDialogBtn("Begin Quest", new Color(30,80,30), new Color(50,140,50));
        JButton cancelB = mkDialogBtn("Cancel", new Color(80,30,30), new Color(120,40,40));
        playB.addActionListener(ev -> {
            SoundManager.playClick();
            String name = nf.getText().trim();
            if (!name.isEmpty()) { d.dispose(); animTimer.stop(); dispose(); SwingUtilities.invokeLater(() -> new GameScreen(name)); }
            else nf.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220,50,50),2),BorderFactory.createEmptyBorder(8,15,8,15)));
        });
        cancelB.addActionListener(ev -> { SoundManager.playClick(); d.dispose(); });
        nf.addActionListener(ev -> playB.doClick());
        bp.add(playB); bp.add(cancelB);
        gc.insets = new Insets(5,0,25,0); gc.fill = GridBagConstraints.NONE; p.add(bp, gc);
        d.setContentPane(p); d.setShape(new RoundRectangle2D.Double(0,0,500,300,30,30)); d.setVisible(true);
    }

    private JButton mkDialogBtn(String text, Color bg, Color hov) {
        JButton btn = new JButton(text) {
            boolean h = false;
            { addMouseListener(new MouseAdapter() { public void mouseEntered(MouseEvent e){h=true;repaint();} public void mouseExited(MouseEvent e){h=false;repaint();} }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2=(Graphics2D)g;g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(h?hov:bg);g2.fill(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),15,15));
                g2.setColor(new Color(255,255,255,60));g2.setStroke(new BasicStroke(1f));g2.draw(new RoundRectangle2D.Double(1,1,getWidth()-2,getHeight()-2,15,15));
                FontMetrics fm=g2.getFontMetrics(getFont());g2.setColor(getForeground());
                g2.drawString(getText(),(getWidth()-fm.stringWidth(getText()))/2,(getHeight()-fm.getHeight())/2+fm.getAscent());
            }
        };
        btn.setFont(new Font("Serif",Font.BOLD,18));btn.setForeground(new Color(220,220,220));btn.setPreferredSize(new Dimension(150,45));
        btn.setContentAreaFilled(false);btn.setBorderPainted(false);btn.setFocusPainted(false);btn.setOpaque(false);btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { try{UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());}catch(Exception e){} new GameUI(); });
    }

    // ===== Background Panel =====
    static class BgPanel extends JPanel {
        Image bgImg; List<float[]> parts = new ArrayList<>(); List<float[]> fogs = new ArrayList<>(); Random r = new Random();
        BgPanel(String path) {
            setDoubleBuffered(true);
            try { File f = new File(path); if(f.exists()) bgImg = ImageIO.read(f); } catch(IOException e){}
            for(int i=0;i<150;i++) parts.add(mkPart(1920,1080,true));
            for(int i=0;i<8;i++) fogs.add(mkFog(1920,1080,true));
        }
        float[] mkPart(int w,int h,boolean ry){float x=r.nextInt(Math.max(w,1));float y=ry?r.nextInt(Math.max(h,1)):h+50;
            float sy=-0.5f-r.nextFloat()*2.5f;float sx=-0.3f+r.nextFloat()*0.6f;float sz=3+r.nextInt(7);float a=0.2f+r.nextFloat()*0.7f;
            float wp=r.nextFloat()*(float)Math.PI*2;float ws=0.02f+r.nextFloat()*0.05f;
            boolean center=x>w*0.35&&x<w*0.65;int cr=center?10+r.nextInt(40):200+r.nextInt(55);int cg=center?100+r.nextInt(100):40+r.nextInt(100);int cb=center?200+r.nextInt(55):r.nextInt(30);
            return new float[]{x,y,sy,sx,sz,a,wp,ws,cr,cg,cb};}
        float[] mkFog(int w,int h,boolean rx){float sz=400+r.nextInt(600);float x=rx?r.nextInt(Math.max(w,1)):-sz;float y=h-sz/2+r.nextInt(300)-200;
            float sp=0.5f+r.nextFloat()*1.5f;float a=0.05f+r.nextFloat()*0.15f;return new float[]{x,y,sz,sp,a};}
        void update(){int w=getWidth(),h=getHeight();
            for(float[] p:parts){p[1]+=p[2];p[0]+=p[3]+(float)Math.sin(p[6])*0.5f;p[6]+=p[7];if(p[1]<h*0.7f)p[5]-=0.002f;
                if(p[1]<-50||p[5]<=0||p[0]<-50||p[0]>w+50){float[] n=mkPart(w,h,false);System.arraycopy(n,0,p,0,n.length);}}
            for(float[] f:fogs){f[0]+=f[3];if(f[0]>w+f[2]){float[] n=mkFog(w,h,false);System.arraycopy(n,0,f,0,n.length);}}}
        @Override protected void paintComponent(Graphics g){super.paintComponent(g);int w=getWidth(),h=getHeight();Graphics2D g2=(Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            if(bgImg!=null){g2.drawImage(bgImg,0,0,w,h,this);g2.setColor(new Color(0,0,0,100));g2.fillRect(0,0,w,h);
                g2.setPaint(new RadialGradientPaint(w/2f,h/2f,w*0.9f,new float[]{0.3f,1},new Color[]{new Color(0,0,0,0),new Color(0,0,0,220)}));g2.fillRect(0,0,w,h);}
            else{g2.setPaint(new GradientPaint(w/2f,0,new Color(15,10,15),w/2f,h,new Color(40,5,10)));g2.fillRect(0,0,w,h);
                g2.setPaint(new RadialGradientPaint(w/2f,h/2f,w*0.8f,new float[]{0,1},new Color[]{new Color(0,0,0,0),new Color(0,0,0,220)}));g2.fillRect(0,0,w,h);}
            Graphics2D fg=(Graphics2D)g.create();
            for(float[] f:fogs){if(f[4]>0){int a=Math.max(0,Math.min(255,(int)(f[4]*255)));
                fg.setPaint(new RadialGradientPaint(f[0]+f[2]/2,f[1]+f[2]/2,f[2]/2,new float[]{0,1},new Color[]{new Color(200,210,220,a),new Color(200,210,220,0)}));
                fg.fill(new RoundRectangle2D.Double(f[0],f[1],f[2],f[2],f[2],f[2]));}}fg.dispose();
            Graphics2D pg=(Graphics2D)g.create();pg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            for(float[] p:parts){if(p[5]>0){int a=Math.max(0,Math.min(255,(int)(p[5]*255)));
                pg.setColor(new Color((int)p[8],(int)p[9],(int)p[10],a/3));float gs=p[4]*2.5f;pg.fill(new RoundRectangle2D.Double(p[0]-gs/2,p[1]-gs/2,gs,gs,gs,gs));
                pg.setColor(new Color((int)p[8],(int)p[9],(int)p[10],a));pg.fill(new RoundRectangle2D.Double(p[0]-p[4]/2,p[1]-p[4]/2,p[4],p[4],p[4],p[4]));}}pg.dispose();}
    }

    // ===== Title Label =====
    static class TitleLabel extends JLabel {
        float pp=0;
        TitleLabel(String t){super(t);setHorizontalAlignment(SwingConstants.CENTER);}
        void pulse(){pp+=0.05f;if(pp>Math.PI*2)pp-=(float)(Math.PI*2);}
        @Override protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            String t=getText();FontMetrics fm=g2.getFontMetrics(getFont());int x=(getWidth()-fm.stringWidth(t))/2;int y=((getHeight()-fm.getHeight())/2)+fm.getAscent();
            g2.setFont(getFont());g2.setColor(new Color(0,0,0,220));g2.drawString(t,x+8,y+8);
            float i=(float)(Math.sin(pp)+1)/2f;int ga=50+(int)(120*i);g2.setColor(new Color(180,20,20,ga));
            int o=3+(int)(i*4);g2.drawString(t,x-o,y-o);g2.drawString(t,x+o,y-o);g2.drawString(t,x-o,y+o);g2.drawString(t,x+o,y+o);
            g2.setColor(new Color(255,150,0,100));g2.drawString(t,x-1,y-1);g2.drawString(t,x+1,y+1);
            g2.setColor(getForeground());g2.drawString(t,x,y);g2.dispose();}
    }

    // ===== Glowing Button =====
    static class GlowBtn extends JButton {
        boolean hov=false;float gf=0;
        Color nBg=new Color(15,10,15,200),hBg=new Color(50,10,15,230),nBd=new Color(100,80,30),hBd=new Color(255,190,50);
        GlowBtn(String t){super(t);setFont(new Font("Serif",Font.BOLD,28));setForeground(new Color(220,220,220));
            setContentAreaFilled(false);setFocusPainted(false);setBorderPainted(false);setOpaque(false);
            setPreferredSize(new Dimension(380,80));setCursor(new Cursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){hov=true;setForeground(Color.WHITE);}
                public void mouseExited(MouseEvent e){hov=false;setForeground(new Color(220,220,220));}});}
        void tick(){if(hov&&gf<1){gf+=0.1f;if(gf>1)gf=1;}else if(!hov&&gf>0){gf-=0.05f;if(gf<0)gf=0;}}
        @Override protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int m=12,bw=getWidth()-m*2,bh=getHeight()-m*2;Shape bs=new RoundRectangle2D.Double(m,m,bw,bh,25,25);
            int br=(int)(nBg.getRed()+(hBg.getRed()-nBg.getRed())*gf),bg2=(int)(nBg.getGreen()+(hBg.getGreen()-nBg.getGreen())*gf);
            int bb=(int)(nBg.getBlue()+(hBg.getBlue()-nBg.getBlue())*gf),ba=(int)(nBg.getAlpha()+(hBg.getAlpha()-nBg.getAlpha())*gf);
            if(gf>0){g2.setColor(new Color(255,30,30,(int)(70*gf)));g2.fill(new RoundRectangle2D.Double(m-6,m-6,bw+12,bh+12,35,35));}
            g2.setColor(new Color(br,bg2,bb,ba));g2.fill(bs);
            int dbr=(int)(nBd.getRed()+(hBd.getRed()-nBd.getRed())*gf),dbg=(int)(nBd.getGreen()+(hBd.getGreen()-nBd.getGreen())*gf);
            int dbb=(int)(nBd.getBlue()+(hBd.getBlue()-nBd.getBlue())*gf);
            g2.setStroke(new BasicStroke(1.5f+1.5f*gf));g2.setColor(new Color(dbr,dbg,dbb));g2.draw(bs);
            FontMetrics fm=g2.getFontMetrics(getFont());int tx=m+(bw-fm.stringWidth(getText()))/2;int ty=m+((bh-fm.getHeight())/2)+fm.getAscent();
            g2.setColor(new Color(0,0,0,150));g2.drawString(getText(),tx+2,ty+2);g2.setColor(getForeground());g2.drawString(getText(),tx,ty);g2.dispose();}
    }
}
