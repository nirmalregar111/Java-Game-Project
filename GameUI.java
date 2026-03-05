import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("The Hidden Key");

        // maximize window
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // icon
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        frame.setIconImage(icon);

        // screen size
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        // background image
        ImageIcon bg = new ImageIcon("background.jpg");
        Image img = bg.getImage();

        Image scaled = img.getScaledInstance(screen.width,screen.height,Image.SCALE_SMOOTH);

        JLabel background = new JLabel(new ImageIcon(scaled));
        background.setLayout(null);

        frame.setContentPane(background);

        // title
        JLabel title = new JLabel("THE HIDDEN KEY");
        title.setFont(new Font("Impact",Font.BOLD,80));
        title.setForeground(new Color(255,220,0));

        title.setBounds(screen.width/2-350,100,700,100);

        // buttons
        JButton start = createButton("START GAME",screen.width/2-100,350);
        JButton instructions = createButton("INSTRUCTIONS",screen.width/2-100,430);
        JButton exit = createButton("EXIT",screen.width/2-100,510);

        background.add(title);
        background.add(start);
        background.add(instructions);
        background.add(exit);

        // start game
        start.addActionListener(e -> {
            frame.dispose();
            Main.main(null);
        });

        // instructions
        instructions.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Explore rooms\nCollect treasure\nFight monsters\nDefeat boss\nFind the hidden key!");
        });

        // exit
        exit.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    static JButton createButton(String text,int x,int y){

        JButton btn = new JButton(text);

        btn.setBounds(x,y,200,50);

        btn.setFont(new Font("Arial",Font.BOLD,18));

        btn.setForeground(Color.WHITE);

        btn.setBackground(new Color(0,0,0,180));

        btn.setFocusPainted(false);

        btn.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));

        btn.addMouseListener(new MouseAdapter(){

            public void mouseEntered(MouseEvent e){
                btn.setBackground(new Color(60,60,60,200));
            }

            public void mouseExited(MouseEvent e){
                btn.setBackground(new Color(0,0,0,180));
            }

        });

        return btn;
    }
}