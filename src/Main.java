
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class Main {
    public static void main(String args[]){
        JFrame frame = new JFrame("SeeTheMusic");
        ImageIcon img = new ImageIcon("monalisa.png");
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Display());
        frame.pack();
        frame.setVisible(true);

    }
}